package com.paymybuddy.paymybuddy.service;


import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Transaction> getTransaction() {
        return transactionRepository.findAll();
    }

    public Iterable<Transaction> findBySenderUser(User user) {
        return transactionRepository.findTransactionBySenderUser(user);
    }

    public Transaction addTransactionWebApp(Transaction transaction) {
        return transactionRepository.save(transaction);
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


    public Page<Transaction> findTransactionPage(Pageable pageable) {

        Iterable<Transaction> transactionIterable = transactionRepository.findAll();
        List<Transaction> transactions = new ArrayList<>();

        for (Transaction transaction : transactionIterable) {
            transactions.add(transaction);
        }

        int pageSize = pageable.getPageSize();
        int curretPage = pageable.getPageNumber();
        int startItem = curretPage * pageSize;

        List<Transaction> list;

        if (transactions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage = new PageImpl<Transaction>(list, PageRequest.of(curretPage, pageSize), transactions.size());

        return transactionPage;
    }


}
