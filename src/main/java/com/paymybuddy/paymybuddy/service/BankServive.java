package com.paymybuddy.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class BankServive {

    public boolean bankValidation(int bankCardId, int bankCardSecurity, String bankCardUserName, float amount) {
        return amount < 2000;
    }
}
