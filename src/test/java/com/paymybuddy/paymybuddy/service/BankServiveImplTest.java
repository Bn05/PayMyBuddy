package com.paymybuddy.paymybuddy.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankServiveImplTest {

    @InjectMocks
    BankServiveImpl bankServive;

    @Test
    void bankValidationLess2000() {

        int bankCardId = 55;
        int bankCardSecurity = 55;
        String bankCardUserName = "FirstName";
        float amount = 1000;

        boolean result = bankServive.bankValidation(bankCardId, bankCardSecurity, bankCardUserName, amount);

        assertTrue(result);
    }

    @Test
    void bankValidationMore2000() {

        int bankCardId = 55;
        int bankCardSecurity = 55;
        String bankCardUserName = "FirstName";
        float amount = 3000;

        boolean result = bankServive.bankValidation(bankCardId, bankCardSecurity, bankCardUserName, amount);

        assertFalse(result);
    }
}