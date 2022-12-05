package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PayMyBuddyApplication implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        Optional<User> user = userRepository.findById(1);
        List<User> contacts = user.get().getContacts();


        for (User contact : contacts){
            String contactName = contact.getFirstName();
            System.out.println(contactName);
        }










    }
}
