package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

    private User user;

    @RequestMapping(value = "")
    public String defaultNoPage() {
        return "redirect:/profilePage";
    }
    @RequestMapping(value = "/")
    public String defaultPage() {
        return "redirect:/profilePage";
    }

    @RequestMapping(value = "/homePage")
    public String homePage(Authentication authentication, Model model) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        user = securityUser.getUser();


        model.addAttribute("user", user);
        return "homePage";
    }


    @GetMapping(value = "/adminPage")
    public String adminPage() {
        return "adminPAge";
    }


}
