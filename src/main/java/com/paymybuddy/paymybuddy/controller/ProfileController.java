package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.BankServive;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

import static java.lang.Math.round;

@Controller
public class ProfileController {

    @Autowired
    BankServive bankServive;

    @Autowired
    UserService userService;

    @Autowired
    private TransactionService transactionService;

    private User user;
    private User payMyBuddy;
    private User yourBank;

    private float amountTransaction;
    float commissionRoundFloat;


    @RequestMapping(value = "/profilePage")
    public String profilePage(Authentication authentication,
                              Model model,
                              @RequestParam(required = false, value = "validationFromBank") boolean validationFromBank,
                              @RequestParam(required = false, value = "rejectFromBank") boolean rejectFromBank,
                              @RequestParam(required = false, value = "validationCommission") boolean validationCommission,
                              @RequestParam(required = false, value = "amountToBank") String amountToBank,
                              @RequestParam(required = false, value = "amountLessCommission") String amountLessCommission,
                              @RequestParam(required = false, value = "commissionRound") String commissionRound,
                              @RequestParam(required = false, value = "walletToLow") boolean walletToLow


    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        user = securityUser.getUser();
        payMyBuddy = userService.getUser(1);
        yourBank = userService.getUser(2);


        model.addAttribute("user", user);
        model.addAttribute("validationFromBank", validationFromBank);
        model.addAttribute("rejectFromBank", rejectFromBank);
        model.addAttribute("validationCommission", validationCommission);
        model.addAttribute("amountToBank", amountToBank);
        model.addAttribute("amountLessCommission", amountLessCommission);
        model.addAttribute("walletToLow", walletToLow);
        model.addAttribute("commissionRound", commissionRound);

        return "profilePage";
    }

    @RequestMapping(value = "/profilePage/modif")
    public String profilePageModif(Model model) {

        String bithdate = user.getBirthdate().toString();

        model.addAttribute("user", user);
        model.addAttribute("birthdate", bithdate);


        return "profilePageModif";
    }


    @PostMapping(value = "/updateAccount")
    public String updateAccount(@Valid @ModelAttribute User updateUser,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/profilePageModif";
        }
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setBirthdate(updateUser.getBirthdate());
        user.setEmail(updateUser.getEmail());
        user.setAddress(updateUser.getAddress());

        if (updateUser.getPassword().equals("")) {
            userService.updateUser(user);
        } else {
            user.setPassword(updateUser.getPassword());
            userService.updateUserWithPassword(user);
        }
        return ("redirect:/profilePage");
    }

    @PostMapping(value = "/addMoneyFromMyBankAccount")
    public ModelAndView addMoneyFromMyBankAccount(@RequestParam(value = "bankCardId") int bankCardId,
                                                  @RequestParam(value = "bankCardSecurity") int bankCardSecurity,
                                                  @RequestParam(value = "bankCardUserName") String bankCardUserName,
                                                  @RequestParam(value = "amount") float amount


    ) {
        ModelAndView modelAndView = new ModelAndView("redirect:/profilePage");

        if (bankServive.bankValidation(bankCardId, bankCardSecurity, bankCardUserName, amount)) {

            user.setWallet(user.getWallet() + amount);
            userService.updateUser(user);

            Transaction transaction = new Transaction(yourBank, user, LocalDate.now(), "From Your Bank", amount);
            transactionService.addTransaction(transaction);

            modelAndView.addObject("validationFromBank", true);
            return modelAndView;
        }
        modelAndView.addObject("rejectFromBank", true);
        return modelAndView;
    }


    @PostMapping(value = "/addMoneyToMyBankAccount")
    public ModelAndView addMoneyToMyBankAccount(@RequestParam(value = "iban") String iban,
                                                @RequestParam(value = "amount") float amount
    ) {
        amountTransaction = amount;

        ModelAndView modelAndView = new ModelAndView("redirect:/profilePage");

        if (user.getWallet() > amountTransaction) {

            double amountTransactionDouble = amountTransaction;
            double commissionPercent = 5.00;
            double commission = amountTransactionDouble * ((commissionPercent) / 100);

            double commissionRound = round(commission * 100.00) / 100.00;
            commissionRoundFloat = (float) commissionRound;

            double amountLessCommission = amount - commissionRound;


            modelAndView.addObject("validationCommission", true);
            modelAndView.addObject("amountToBank", String.valueOf(amount));
            modelAndView.addObject("amountLessCommission", amountLessCommission);
            modelAndView.addObject("commissionRound", commissionRound);

        } else {
            modelAndView.addObject("walletToLow", true);
        }
        return modelAndView;
    }

    @GetMapping(value = "/addMoneyToMyBankAccount/validation")
    public String addMoneyToMyBankAccountValidation() {

        user.setWallet(user.getWallet() - amountTransaction);
        userService.updateUser(user);

        payMyBuddy.setWallet(payMyBuddy.getWallet() + commissionRoundFloat);
        userService.updateUser(payMyBuddy);

        Transaction transaction = new Transaction(user, yourBank, LocalDate.now(), "To Your Bank", amountTransaction);
        transactionService.addTransaction(transaction);


        return "redirect:/profilePage";
    }
}
