package com.paymybuddy.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class BankServiveImpl implements BankService{

    @Override
    public boolean bankValidation(int bankCardId, int bankCardSecurity, String bankCardUserName, float amount) {
        return amount < 2000;
    }
}
