package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "/contactPage")
    public String contactPage(Authentication authentication,
                              @RequestParam(required = false, value = "alwaysYourContact") boolean alwaysYourContact,
                              @RequestParam(required = false, value = "noCustomer") boolean noCustomer,
                              @RequestParam(required = false, value = "itsYou") boolean itsYou,
                              Model model
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        Iterable<User> userContacts = user.getContacts();

        model.addAttribute("alwaysYourContact", alwaysYourContact);
        model.addAttribute("noCustomer", noCustomer);
        model.addAttribute("userContacts", userContacts);
        model.addAttribute("itsYou", itsYou);

        return "contactPage";
    }


    @RequestMapping(value = "/addContact")
    public ModelAndView addContact(Authentication authentication, Model model, @RequestParam(value = "email") String email) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
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

        List<User> contacts = user.getContacts();
        contacts.add(newContact);
        userService.updateUser(user);

        return modelAndView;

    }

    @GetMapping(value = "/contactPage/deleteContact")
    public String deleteContact(Authentication authentication,
                                @RequestParam(value = "email") String email
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        List<User> contacts = user.getContacts();

        for (User contact : contacts) {
            if (contact.getEmail().equals(email)) {
                contacts.remove(contact);
                break;
            }
        }

        user.setContacts(contacts);
        userService.updateUser(user);
        return "redirect:/contactPage";
    }


}
