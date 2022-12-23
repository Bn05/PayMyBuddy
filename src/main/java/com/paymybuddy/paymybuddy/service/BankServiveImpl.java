package com.paymybuddy.paymybuddy.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankServiveImpl implements BankService{

    @Override
    public boolean bankValidation(int bankCardId, int bankCardSecurity, String bankCardUserName, float amount) {
        return amount < 2000;
    }
}
