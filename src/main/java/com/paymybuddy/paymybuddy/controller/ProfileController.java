package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/profilePage")
    public String profilePage(Authentication authentication, Model model) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        model.addAttribute("user", user);

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
}
