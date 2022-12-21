package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    final
    UserService userService;
    private User user;

    public HomePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String defaultNoPage() {
        return "redirect:/homePage";
    }

    @RequestMapping(value = "/")
    public String defaultPage() {
        return "redirect:/homePage";
    }

    @RequestMapping(value = "/homePage")
    public String homePage(Authentication authentication, Model model) {
       user = userService.getCurrentUser(authentication);
        model.addAttribute("user", user);
        return "homePage";
    }
}
