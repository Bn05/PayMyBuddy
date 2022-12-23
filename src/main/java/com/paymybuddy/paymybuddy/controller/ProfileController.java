package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;


@Controller
public class ProfileController {

    private final BankService bankService;

    private final UserService userService;

    private final TransactionService transactionService;

    private final FacturationService facturationService;

    private User user;
    private User payMyBuddy;
    private User yourBank;

    private float amountTransaction;
    float commissionRoundFloat;


    public ProfileController(BankService bankService, UserService userService, TransactionService transactionService, FacturationService facturationService) {
        this.bankService = bankService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.facturationService = facturationService;
    }


    @GetMapping(value = "/profilePage")
    public String profilePage(Authentication authentication,
                              Model model,
                              @RequestParam(required = false, value = "validationFromBank") boolean validationFromBank,
                              @RequestParam(required = false, value = "rejectFromBank") boolean rejectFromBank,
                              @RequestParam(required = false, value = "validationCommission") boolean validationCommission,
                              @RequestParam(required = false, value = "amountToBank") String amountToBank,
                              @RequestParam(required = false, value = "amountLessCommission") String amountLessCommission,
                              @RequestParam(required = false, value = "commissionRound") String commissionRound,
                              @RequestParam(required = false, value = "commissionPerCent") String commissionPerCent,
                              @RequestParam(required = false, value = "walletToLow") boolean walletToLow


    ) {
        user = userService.getCurrentUser(authentication);
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
        model.addAttribute("commissionPerCent", commissionPerCent);

        return "profilePage";
    }

    @GetMapping(value = "/profilePage/modif")
    public String profilePageModif(Model model) {

        String birthdate = user.getBirthdate().toString();
        model.addAttribute("user", user);
        model.addAttribute("birthdate", birthdate);


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

        if (bankService.bankValidation(bankCardId, bankCardSecurity, bankCardUserName, amount)) {

            Map<String, Double> resultMap = facturationService.getCommission(amount);


            user.setWallet((float) (user.getWallet() + resultMap.get("amountLessCommission")));
            userService.updateUser(user);

            double commissionDouble = resultMap.get("commissionRound");
            float commissionFloat = (float) commissionDouble;

            payMyBuddy.setWallet(payMyBuddy.getWallet() + commissionFloat);
            userService.updateUser(payMyBuddy);

            Transaction transaction = new Transaction(yourBank, user, LocalDate.now(), "From Your Bank", amount, commissionFloat);
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

            Map<String, Double> resultMap = facturationService.getCommission(amountTransaction);

            double commissionRoundDouble = resultMap.get("commissionRound");
            commissionRoundFloat = (float) commissionRoundDouble;

            modelAndView.addObject("validationCommission", true);
            modelAndView.addObject("amountToBank", String.valueOf(amount));
            modelAndView.addObject("amountLessCommission", resultMap.get("amountLessCommission"));
            modelAndView.addObject("commissionRound", resultMap.get("commissionRound"));
            modelAndView.addObject("commissionPerCent", resultMap.get("commissionPerCent"));

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

        Transaction transaction = new Transaction(user, yourBank, LocalDate.now(), "To Your Bank", amountTransaction, commissionRoundFloat);
        transactionService.addTransaction(transaction);


        return "redirect:/profilePage";
    }
}
