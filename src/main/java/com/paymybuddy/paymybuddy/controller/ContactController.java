package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.exceptions.ContactNoExistException;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.ContactService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/contacts")
    public Iterable<User> getContacts(@RequestParam(value = "id") int id) {
        return contactService.getContacts(id);
    }

   // TODO : Deja dans la liste des contacts.
    @PostMapping(value = "/contact")
    public User addContact(@RequestParam(value = "userId") int userId, @RequestParam(value = "contactEmail") String contactEmail) {
        User contact = userService.findUserByEmail(contactEmail);
        if (contact != null) {
             return contactService.addContact(userId, contact.getUserId());
        } else throw new ContactNoExistException("Le contact n'existe pas");
    }

    //TODO : Supprimer un contact avec ses erreurs
}
