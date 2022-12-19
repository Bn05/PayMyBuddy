package com.paymybuddy.paymybuddy.service;

public interface BankService {

    public boolean bankValidation(int bankCardId, int bankCardSecurity, String bankCardUserName, float amount);
}
