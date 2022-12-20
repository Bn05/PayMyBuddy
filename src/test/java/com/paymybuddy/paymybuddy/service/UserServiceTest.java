package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Role;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.RoleRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    RoleRepository roleRepositoryMock;

    User user1;
    User user2;

    @BeforeEach
    void SetUp() {
        List<User> contacts = new ArrayList<>();
        user1 = new User("firstName1", "lastName2", LocalDate.of(2022, 12, 19), "na@na.fr", "Toulouse", 150f, "password", contacts);
        user2 = new User("firstName2", "lastName2", LocalDate.of(2022, 12, 20), "na2@na2.fr", "Toulouse2", 150f, "password2", contacts);
    }

    @Test
    void getUser() {
        when(userRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(user1));

        User userResult = userService.getUser(0);

        assertEquals(user1, userResult);
    }

    @Test
    void getUserByEmail() {
        when(userRepositoryMock.findByEmail("na@na.fr")).thenReturn(Optional.ofNullable(user1));

        User userResult = userService.getUserByEmail("na@na.fr").get();

        assertEquals(user1, userResult);
    }

    @Test
    void saveUser() {
        Optional<Role> role = Optional.of(new Role());
        when(roleRepositoryMock.findById(anyInt())).thenReturn(role);

        userService.saveUser(user1);

        assertNotEquals("password", user1.getPassword());
        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    void updateUser() {
        userService.updateUser(user1);

        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    void updateUserWithPassword() {
        userService.updateUserWithPassword(user1);

        assertNotEquals("password", user1.getPassword());
        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    void addContact() {
        when(userRepositoryMock.findByEmail("na2@na2.fr")).thenReturn(Optional.ofNullable(user2));

        userService.addContact(user1, "na2@na2.fr");

        User userResult = user1.getContacts().get(0);

        assertEquals(user2, userResult);
        verify(userRepositoryMock, times(1)).save(user1);
    }

    @Test
    void deleteContact() {
        List<User> contacts = new ArrayList<>();
        contacts.add(user2);
        user1.setContacts(contacts);

        userService.deleteContact(user1, "na2@na2.fr");

        List<User> contactsResult = user1.getContacts();

        boolean result = contactsResult.contains(user2);

        assertFalse(result);
        verify(userRepositoryMock, times(1)).save(user1);
    }
}