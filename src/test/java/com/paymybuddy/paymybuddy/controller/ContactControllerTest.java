package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ContactController contactController;

    @MockBean
    UserService userServiceMock;

    private User user1;
    private User user2;

    private Optional<User> optionalUser;

    @BeforeAll
    void beforeAll() {
        List<User> contacts = new ArrayList<>();
        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
        user1.setUserId(1);
        user2.setUserId(2);
    }

    @Test
    void contactPage() throws Exception {

        when(userServiceMock.getCurrentUser(any())).thenReturn(user1);

        mockMvc.perform(get("/contactPage").with(user(new SecurityUser(user1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addContact() throws Exception {

        ReflectionTestUtils.setField(contactController, "user", user1);
        optionalUser = Optional.ofNullable(user2);

        when(userServiceMock.getUserByEmail(any())).thenReturn(optionalUser);

        mockMvc.perform(get("/addContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void addContactNoCustumer() throws Exception {

        ReflectionTestUtils.setField(contactController, "user", user1);
       optionalUser = Optional.empty();

        when(userServiceMock.getUserByEmail(any())).thenReturn(optionalUser);

        mockMvc.perform(get("/addContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void addContactItsYou() throws Exception {

        ReflectionTestUtils.setField(contactController, "user", user1);
        optionalUser = Optional.ofNullable(user1);

        when(userServiceMock.getUserByEmail(any())).thenReturn(optionalUser);

        mockMvc.perform(get("/addContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void addContactAlwaysContact() throws Exception {
        List<User> contacts = new ArrayList<>();
        contacts.add(user2);
        user1.setContacts(contacts);

        ReflectionTestUtils.setField(contactController, "user", user1);
        optionalUser = Optional.ofNullable(user2);

        when(userServiceMock.getUserByEmail(any())).thenReturn(optionalUser);

        mockMvc.perform(get("/addContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }



    @Test
    void deleteContact() throws Exception {

        mockMvc.perform(get("/contactPage/deleteContact").with(user(new SecurityUser(user1)))
                        .param("email", "na@na.fr")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());


    }
}