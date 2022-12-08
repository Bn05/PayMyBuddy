package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/home")
    public String homePage(Model model) {

        Iterable<Transaction> transactions = transactionService.getTransaction();
        model.addAttribute("transactions", transactions);

        User user = new User();
        model.addAttribute("user", user);

        return "home";
    }

    @RequestMapping(value = "/test")
    public String test(Model model) {

        Iterable<Transaction> transactions = transactionService.getTransaction();
        model.addAttribute("trans", transactions);

        Iterable<User> users = userService.findUsers();
        model.addAttribute("employees", users);
        return "test";
    }







}
