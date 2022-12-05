package com.paymybuddy.paymybuddy.service;


import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Transaction> getTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction addTransaction(int senderUserId, int receivingUserId, int transactionAmount) {

        User senderUser = userRepository.findById(senderUserId).get();
        senderUser.setWallet(senderUser.getWallet() - transactionAmount);
        userRepository.save(senderUser);

        User receivingUser = userRepository.findById(receivingUserId).get();
        receivingUser.setWallet(receivingUser.getWallet() + transactionAmount);
        userRepository.save(receivingUser);

        Transaction transaction = new Transaction(senderUser, receivingUser, LocalDate.now(), transactionAmount);
        return transactionRepository.save(transaction);
    }
}
