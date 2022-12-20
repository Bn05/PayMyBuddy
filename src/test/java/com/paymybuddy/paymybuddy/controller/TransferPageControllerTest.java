package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferPageControllerTest {

    @InjectMocks
    TransferPageController transferPageController;
    @Autowired
    public MockMvc mockMvc;

    User user1;
    User user2;

    Transaction transaction1;


    @BeforeAll
    void beforeAll() {
        List<User> contacts = new ArrayList<>();
        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
        transaction1 = new Transaction();
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void transferPage() throws Exception {

        mockMvc.perform(get("/transferPage")
                        .param("page", "1")
                        .param("size", "3")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.view().name("transferPage"))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void addTransaction() {
    }


}
