package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferPageControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    UserService userServiceMock;

    @MockBean
    TransactionService transactionServiceMock;

    User user1;
    User user2;
    Transaction transaction1;

    Page<Transaction> transactionPage;


    @BeforeAll
    void beforeAll() {
        List<User> contacts = new ArrayList<>();
        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
        contacts.add(user2);
        user1.setContacts(contacts);
        transaction1 = new Transaction(user1, user2, LocalDate.of(2022, 12, 20), "comtest", 15.0f, 2f);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);

        transactionPage = new PageImpl<>(transactions);

    }


    @Test
    public void transferPage() throws Exception {

        when(userServiceMock.getCurrentUser(any())).thenReturn(user1);
        when(transactionServiceMock.findTransactionPage(any(),any())).thenReturn(transactionPage);

        mockMvc.perform(get("/transferPage").with(user(new SecurityUser(user1)))
                        .param("page", "1")
                        .param("size", "3")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addTransaction() throws Exception {

        when(userServiceMock.getCurrentUser(any())).thenReturn(user1);
        when(userServiceMock.getUser(anyInt())).thenReturn(user2);

        doNothing().when(userServiceMock).updateUser(any());

        when(transactionServiceMock.addTransaction(any())).thenReturn(null);


        mockMvc.perform(get("/addTransaction").with(user(new SecurityUser(user1)))
                .param("contact", "1")
                .param("amount", "10")
                .param("comment", "testa")
                .with(csrf())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

    }
}
