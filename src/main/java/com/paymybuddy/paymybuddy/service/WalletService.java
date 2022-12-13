package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WalletService {

    @Autowired
    private UserRepository userRepository;

    public float getWallet(int userId) {
        return userRepository.findById(userId).get().getWallet();
    }

    public float addMoneyToWallet(int userId, int amountTransaction) {
        User user = userRepository.findById(userId).get();
        float userWallet = getWallet(userId);

        userWallet = userWallet + amountTransaction;
        user.setWallet(userWallet);
        userRepository.save(user);

        return userWallet;
    }

    public float transfertMoneyToBank(int userId, int amountTransaction) {
        User user = userRepository.findById(userId).get();
        float userWallet = getWallet(userId);
        float commission = 1;

        userWallet = userWallet - amountTransaction*commission ;
        user.setWallet(userWallet);
        userRepository.save(user);

        return userWallet;

    }
}
