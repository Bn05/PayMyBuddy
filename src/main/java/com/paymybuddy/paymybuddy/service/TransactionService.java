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

    public Iterable<Transaction> getTransaction() {
        return transactionRepository.findAll();
    }

    public Iterable<Transaction> findBySenderUser(User user) {
        return transactionRepository.findTransactionBySenderUser(user);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> findTransactionPage(Pageable pageable) {

        Iterable<Transaction> transactionIterable = transactionRepository.findAll();
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionIterable) {
            transactions.add(transaction);
        }

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Transaction> list;

        if (transactions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage = new PageImpl<Transaction>(list, PageRequest.of(currentPage, pageSize), transactions.size());

        return transactionPage;
    }


}
