package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public User getUsers(@RequestParam(value = "id") int id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Update User, we need give id
    @PutMapping(value = "/user")
    public User updateUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam(value = "id") int id) {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }
}
