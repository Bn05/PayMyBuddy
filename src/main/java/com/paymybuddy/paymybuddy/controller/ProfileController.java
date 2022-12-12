package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.BankServive;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class ProfileController {

    @Autowired
    BankServive bankServive;

    @Autowired
    UserService userService;

    private float amountTransaction;

    @RequestMapping(value = "/profilePage")
    public String profilePage(Authentication authentication,
                              Model model,
                              @RequestParam(required = false, value = "validationFromBank") boolean validationFromBank,
                              @RequestParam(required = false, value = "rejectFromBank") boolean rejectFromBank,
                              @RequestParam(required = false, value = "validationCommission") boolean validationCommission,
                              @RequestParam(required = false, value = "amountToBank") String amountToBank,
                              @RequestParam(required = false, value = "amountLessCommission") String amountLessCommission,
                              @RequestParam(required = false, value = "walletToLow") boolean walletToLow

    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();


        model.addAttribute("user", user);
        model.addAttribute("validationFromBank", validationFromBank);
        model.addAttribute("rejectFromBank", rejectFromBank);
        model.addAttribute("validationCommission", validationCommission);
        model.addAttribute("amountToBank", amountToBank);
        model.addAttribute("amountLessCommission", amountLessCommission);
        model.addAttribute("walletToLow", walletToLow);

        return "/profilePage";
    }

    @RequestMapping(value = "/profilePage/modif")
    public String profilePageModif(Authentication authentication, Model model) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        String bithdate = user.getBirthdate().toString();

        model.addAttribute("user", user);
        model.addAttribute("birthdate", bithdate);


        return "/profilePageModif";
    }


    @PostMapping(value = "/updateAccount")
    public String updateAccount(Authentication authentication, @ModelAttribute User updateUser) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

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
    public ModelAndView addMoneyFromMyBankAccount(Authentication authentication,
                                                  @RequestParam(value = "bankCardId") int bankCardId,
                                                  @RequestParam(value = "bankCardSecurity") int bankCardSecurity,
                                                  @RequestParam(value = "bankCardUserName") String bankCardUserName,
                                                  @RequestParam(value = "amount") float amount


    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        ModelAndView modelAndView = new ModelAndView("redirect:/profilePage");

        if (bankServive.bankValidation(bankCardId, bankCardSecurity, bankCardUserName, amount)) {
            user.setWallet(user.getWallet() + amount);
            userService.updateUser(user);

            modelAndView.addObject("validationFromBank", true);
            return modelAndView;
        }

        modelAndView.addObject("rejectFromBank", true);
        return modelAndView;
    }


    @PostMapping(value = "/addMoneyToMyBankAccount")
    public ModelAndView addMoneyToMyBankAccount(Authentication authentication,
                                                @RequestParam(value = "iban") String iban,
                                                @RequestParam(value = "amount") float amount
    ) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        ModelAndView modelAndView = new ModelAndView("redirect:/profilePage");

        if (user.getWallet() > amount) {

            modelAndView.addObject("validationCommission", true);
            amountTransaction = amount;
            modelAndView.addObject("amountToBank", String.valueOf(amount));
            modelAndView.addObject("amountLessCommission", (amount * 0.95));

        } else {
            modelAndView.addObject("walletToLow", true);
        }
        return modelAndView;
    }

    @GetMapping(value = "/addMoneyToMyBankAccount/validation")
    public String addMoneyToMyBankAccountValidation(Authentication authentication

    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        user.setWallet(user.getWallet() - amountTransaction);
        userService.updateUser(user);


        return "redirect:/profilePage";
    }
}
