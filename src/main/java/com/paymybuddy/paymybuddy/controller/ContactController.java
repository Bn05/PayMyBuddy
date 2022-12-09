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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    public UserService userService;

    private User newContact;

    @RequestMapping(value = "/contactPage")
    public String contactPage() {
        return "contactPage";
    }


    @RequestMapping(value = "/addContact")
    public ModelAndView addContact(Authentication authentication, Model model, @RequestParam(value = "email") String email) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        newContact = userService.getUserByEmail(email).orElse(null);


        if (newContact == null) {
            boolean error = true;
            String errorNoClient = "La personne à qui appartient cette adresse n'est pas encore inscrit à Pay My Byddy. Invitez le :)";
            model.addAttribute("errorNoClient", errorNoClient);

            return new ModelAndView("redirect:/contactPage");
        } else if (user.getContacts().contains(newContact)) {

            String errorAlwaysContact = "Deja votre contact";
            model.addAttribute("errorAlwaysContact", errorAlwaysContact);

        }
        return new ModelAndView("redirect:/contactPage/validation");

    }

    @RequestMapping(value = "/contactPage/validation")
    public String addContactValidation(Model model) {

        String contactFirstName = newContact.getFirstName();
        String contactLastName = newContact.getLastName();

        model.addAttribute("contactFirstName", contactFirstName);
        model.addAttribute("contactLastName", contactLastName);

        return "addValidationContactPage";
    }

    @RequestMapping(value = "/contactPage/validation/yes")
    public String addContactValidationYes(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        List<User> contacts = user.getContacts();
        contacts.add(newContact);
        userService.updateUser(user);

        return "contactPage";
    }

    @GetMapping(value = "/contactPage/validation/no")
    public String addContactValidationNo() {
        return "contactPage";
    }

}
