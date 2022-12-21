package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userServiceMock;

    private User user1;
    private User user2;

    @BeforeAll
    void beforeAll() {
        List<User> contacts = new ArrayList<>();
        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
    }

    @Test
    void contactPage() throws Exception {

        mockMvc.perform(get("/contactPage").with(user(new SecurityUser(user1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addContact() throws Exception {

        when(userServiceMock.getUserByEmail(any())).thenReturn(Optional.ofNullable(user2));

        mockMvc.perform(post("/addContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void deleteContact() {
    }
}