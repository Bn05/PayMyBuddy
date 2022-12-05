package com.paymybuddy.paymybuddy.service;

import org.springframework.stereotype.Service;

@Service
public class BankServive {

    public boolean bankValidation(String informationVisa) {
        return !informationVisa.isBlank();
    }
}
