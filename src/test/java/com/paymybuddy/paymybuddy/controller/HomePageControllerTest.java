package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class HomePageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserService userServiceMock;

    @Test
    void defaultNoPage() throws Exception {

        mockMvc.perform(get(""))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void defaultPage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }


    @Test
    void homePage() throws Exception {
        List<User> contacts = new ArrayList<>();
        User user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);

        when(userServiceMock.getCurrentUser(any())).thenReturn(user1);

        mockMvc.perform(get("/homePage").with(user(new SecurityUser(user1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
