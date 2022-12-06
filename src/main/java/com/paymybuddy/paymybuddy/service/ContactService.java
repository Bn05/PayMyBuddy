package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getContacts(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.get().getContacts();
    }


    public User addContact (int userId, int contactId){
        User user = userRepository.findById(userId).get();
        User contact = userRepository.findById(contactId).get();

        List<User> contacts = user.getContacts();
        contacts.add(contact);

        userRepository.save(user);
        return contact ;
    }
}
