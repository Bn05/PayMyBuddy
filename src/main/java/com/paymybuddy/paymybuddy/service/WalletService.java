package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private UserRepository userRepository;

    public int getWallet(int userId) {
        return userRepository.findById(userId).get().getWallet();
    }

    public int addMoneyToWallet(int userId, int amountTransaction) {
        User user = userRepository.findById(userId).get();
        int userWallet = getWallet(userId);

        userWallet = userWallet + amountTransaction;
        user.setWallet(userWallet);
        userRepository.save(user);

        return userWallet;
    }

    // TODO: Wallet int -> double ou float

    public int transfertMoneyToBank(int userId, int amountTransaction) {
        User user = userRepository.findById(userId).get();
        int userWallet = getWallet(userId);
        int commission = 1;

        userWallet = userWallet - amountTransaction*commission ;
        user.setWallet(userWallet);
        userRepository.save(user);

        return userWallet;

    }
}
