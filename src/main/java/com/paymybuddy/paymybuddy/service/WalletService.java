package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private UserRepository userRepository;

    public int getWallet(int id) {
        return userRepository.findById(id).get().getWallet();
    }
}
