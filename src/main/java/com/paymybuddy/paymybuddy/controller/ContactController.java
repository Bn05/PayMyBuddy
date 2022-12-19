package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ContactController {

    public final UserService userService;

    public ContactController(UserService userService) {
        this.userService = userService;
    }

    private User user;

    @RequestMapping(value = "/contactPage")
    public String contactPage(Authentication authentication,
                              @RequestParam(required = false, value = "alwaysYourContact") boolean alwaysYourContact,
                              @RequestParam(required = false, value = "noCustomer") boolean noCustomer,
                              @RequestParam(required = false, value = "itsYou") boolean itsYou,
                              Model model
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        user = securityUser.getUser();

        Iterable<User> userContacts = user.getContacts();

        model.addAttribute("alwaysYourContact", alwaysYourContact);
        model.addAttribute("noCustomer", noCustomer);
        model.addAttribute("userContacts", userContacts);
        model.addAttribute("itsYou", itsYou);

        return "contactPage";
    }


    @RequestMapping(value = "/addContact")
    public ModelAndView addContact(@RequestParam(value = "email") String email) {

        User newContact = userService.getUserByEmail(email).orElse(null);
        ModelAndView modelAndView = new ModelAndView("redirect:contactPage");

        if (newContact == null) {
            modelAndView.addObject("noCustomer", true);
            return modelAndView;
        }

        if (newContact.getUserId() == user.getUserId()) {
            modelAndView.addObject("itsYou", true);
            return modelAndView;
        }

        for (User contact : user.getContacts()) {
            if (contact.getUserId() == (newContact.getUserId())) {
                modelAndView.addObject("alwaysYourContact", true);
                return modelAndView;
            }
        }

        userService.addContact(user, email);

        return modelAndView;
    }

    @GetMapping(value = "/contactPage/deleteContact")
    public String deleteContact(
                                @RequestParam(value = "email") String email
    ) {
       userService.deleteContact(user, email);

        return "redirect:/contactPage";
    }


}
