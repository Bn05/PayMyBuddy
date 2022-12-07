package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.exceptions.SenderWalletToLowException;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    WalletService walletService;

    @GetMapping(value = "/transaction")
    public Iterable<Transaction> getTransaction(Authentication authentication) {
        return transactionService.getTransaction();
    }

    @PostMapping(value = "/transaction")
    public Transaction addTransaction(
            @RequestParam(value = "senderUserId") int senderUserId,
            @RequestParam(value = "receivingUserId") int receivingUser,
            @RequestParam(value = "transactionAmount") int transactionAmount) {

        if(walletService.getWallet(senderUserId)>transactionAmount) {
            return transactionService.addTransaction(senderUserId, receivingUser, transactionAmount);
        }
        throw new SenderWalletToLowException("Votre Wallet n'est pas assez rempli." );
    }

}
