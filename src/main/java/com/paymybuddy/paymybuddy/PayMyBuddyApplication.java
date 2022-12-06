package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.configuration.SpringSecurityConfig;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
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


    }
}
