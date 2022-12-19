package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class JpaUserDetailsServiceTest {

    @InjectMocks
    JpaUserDetailsService jpaUserDetailsService;

    @Mock
    UserRepository userRepositoryMock;

    @Test
    void loadUserByUsername() {

        User user = new User();

        when(userRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(user));

       UserDetails securityUser =  jpaUserDetailsService.loadUserByUsername("email");

       assertNotNull(securityUser);
    }
}