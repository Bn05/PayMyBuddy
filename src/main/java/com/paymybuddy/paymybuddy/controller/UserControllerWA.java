package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserControllerWA {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/creationAccount")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "creationAccount";
    }

    @RequestMapping(value = "/saveUser")
    public ModelAndView saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/validateCreationAccount");
    }

    @RequestMapping(value = "/validateCreationAccount")
    public String validateCreationAccount(Model model) {
        return "validateCreationAccount";
    }

}
