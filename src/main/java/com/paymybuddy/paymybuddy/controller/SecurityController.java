package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SecurityController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/creationAccount")
    public String newUser(Model model, User user) {

        model.addAttribute("user", user);

        return "creationAccount";    }

    @PostMapping(value = "/creationAccount")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult) {



        if(bindingResult.hasErrors()){
            return "/creationAccount";
        }

        userService.saveUser(user);
        return ("redirect:/validateCreationAccount");
    }

    @RequestMapping(value = "/validateCreationAccount")
    public String validateCreationAccount(Model model) {
        return "validateCreationAccount";
    }

    @GetMapping(value = "/logoutfrfrf")
    public String logout()
    {return "/logout";}




}
