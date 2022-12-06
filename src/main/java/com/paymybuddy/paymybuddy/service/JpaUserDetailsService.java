package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userService.findUserByEmail(email);
      return new org.springframework.security.core.userdetails.User(user.getFirstName(),user.getPassword(),Collections.emptyList());
    }
}
