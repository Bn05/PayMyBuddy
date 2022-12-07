package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

    public User findUserByFirstName(String firstName){return userRepository.findByFirstName(firstName).get();}

    public User findUserByEmail(String email) {
        Optional<User> searchResult = userRepository.findByEmail(email);
        return searchResult.orElse(null);
    }

    public User findUserById (int id){
        Optional<User> searchResult = userRepository.findById(id);
        return searchResult.orElse(null);
    }

    public Iterable<User> findUsers() {
        return userRepository.findAll();
    }


    public User saveUser(User user) {
        String password = user.getPassword();
        String passwordBcrypt = passwordEncoder().encode(password);
        user.setPassword(passwordBcrypt);

        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


}
