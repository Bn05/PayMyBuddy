package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.configuration.SpringSecurityConfig;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PayMyBuddyApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    SpringSecurityConfig springSecurityConfig;


    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        Iterable<User> users = userService.getUsers();

        for (User user : users)
        {
            String userPassword = user.getPassword();
            userPassword = springSecurityConfig.passwordEncoder().encode(userPassword);
            user.setPassword(userPassword);
            userService.saveUser(user);
        }


    }
}
