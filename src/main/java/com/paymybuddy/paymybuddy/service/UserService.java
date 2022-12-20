package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.RoleRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

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

    public void addContact(User user, String email) {
        List<User> contacts = user.getContacts();
        User newContact = userRepository.findByEmail(email).get();
        contacts.add(newContact);

        updateUser(user);
    }

    public void deleteContact(User user, String email) {

        List<User> contacts = user.getContacts();

        for (User contact : contacts) {
            if (contact.getEmail().equals(email)) {
                contacts.remove(contact);
                break;
            }
        }
        updateUser(user);
    }

    public User getCurrentUser(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        return user;
    }
}
