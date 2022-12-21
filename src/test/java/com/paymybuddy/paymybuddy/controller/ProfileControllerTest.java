package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.BankService;
import com.paymybuddy.paymybuddy.service.FacturationService;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    ProfileController profileController;

    @Mock
    BankService bankServiceMock;

    @MockBean
    UserService userServiceMock;

    @MockBean
    TransactionService transactionServiceMock;

    @MockBean
    FacturationService facturationServiceMock;

    User user1;
    User user2;

    User payMyBuddy;
    User yourBank;

    @BeforeAll
    void beforeAll() {
        List<User> contacts = new ArrayList<>();

        payMyBuddy = new User("payMyBuddy", "payMyBuddy", LocalDate.of(2022, 12, 19), "payMyBuddy@payMyBuddy.fr", "Toulouse", 150f, "password", contacts);
        yourBank = new User("yourBank", "yourBank", LocalDate.of(2022, 12, 19), "yourBank@yourBank.fr", "Toulouse", 150f, "password", contacts);

        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
        contacts.add(user2);
        user1.setContacts(contacts);

    }

    @Test
    void profilePage() throws Exception {

        when(userServiceMock.getCurrentUser(any())).thenReturn(user1);
        when(userServiceMock.getUser(1)).thenReturn(payMyBuddy);
        when(userServiceMock.getUser(2)).thenReturn(yourBank);

        mockMvc.perform(get("/profilePage").with(user(new SecurityUser(user1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void profilePageModif() throws Exception {

        ReflectionTestUtils.setField(profileController, "user", user1);

        mockMvc.perform(get("/profilePage/modif").with(user(new SecurityUser(user1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateAccount() throws Exception {

        ReflectionTestUtils.setField(profileController, "user", user1);

        mockMvc.perform(post("/updateAccount").with(user(new SecurityUser(user1)))
                        .flashAttr("updateUser", user2)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addMoneyFromMyBankAccount() throws Exception {

        ReflectionTestUtils.setField(profileController, "user", user1);
        when(bankServiceMock.bankValidation(anyInt(),anyInt(),anyString(),anyInt())).thenReturn(true);

        mockMvc.perform(post("/addMoneyFromMyBankAccount").with(user(new SecurityUser(user1)))
                        .param("bankCardId", "155")
                        .param("bankCardSecurity", "155")
                        .param("bankCardUserName", "155")
                        .param("amount", "15")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void addMoneyToMyBankAccount() throws Exception {

        ReflectionTestUtils.setField(profileController, "user", user1);

        mockMvc.perform(post("/addMoneyToMyBankAccount").with(user(new SecurityUser(user1)))
                        .param("iban", "10")
                        .param("amount", "15")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void addMoneyToMyBankAccountValidation() throws Exception {

        ReflectionTestUtils.setField(profileController, "user", user1);
        ReflectionTestUtils.setField(profileController, "payMyBuddy", payMyBuddy);
        ReflectionTestUtils.setField(profileController, "yourBank", yourBank);

        mockMvc.perform(get("/addMoneyToMyBankAccount/validation").with(user(new SecurityUser(user1)))
                                              .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}