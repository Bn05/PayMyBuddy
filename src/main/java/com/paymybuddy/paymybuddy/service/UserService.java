package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.RoleRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        String password = user.getPassword();
        String passwordBcrypt = passwordEncoder().encode(password);

        user.setPassword(passwordBcrypt);
        user.setWallet(0);
        user.setRole(roleRepository.findById(1).get());

        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }


    public void updateUserWithPassword(User user) {
        String password = user.getPassword();
        String passwordBcrypt = passwordEncoder().encode(password);
        user.setPassword(passwordBcrypt);

        userRepository.save(user);
    }

}
