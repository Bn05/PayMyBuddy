package com.paymybuddy.paymybuddy.controller;

import com.nimbusds.jose.util.Base64URL;
import com.paymybuddy.paymybuddy.exceptions.ContactNoExistException;
import com.paymybuddy.paymybuddy.exceptions.UserAlreadyExistException;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
public class UserController {

        private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public User getUsers(@RequestParam(value = "id") int id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        String userMail = user.getEmail();
        User userAlreadyExist = userService.getUserByEmail(userMail);

        if (userAlreadyExist == null) {
            return userService.saveUser(user);
        } else throw new UserAlreadyExistException("Adresse mail déjà utilisée");

    }

   // TODO : empecher la créeation depuis put & Erreur si USer n'existe pas ou mauvaise id
    @PutMapping(value = "/user")
    public User updateUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // TODO :  Erreur si suppresion UserNoExist
    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam(value = "id") int id) {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }
}
