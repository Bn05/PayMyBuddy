package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.exceptions.SenderWalletToLowException;
import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class TransactionControllerWA {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addTransaction")
    public ModelAndView addTransaction(Authentication authentication,
                                       @ModelAttribute Transaction transactionRequest
    ) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        User senderUser = securityUser.getUser();
        User receivingUser = userService.getUser(transactionRequest.getReceivingUser().getUserId());

        transactionRequest.setSenderUser(senderUser);
        transactionRequest.setReceivingUser(receivingUser);
        transactionRequest.setTransactionDate(LocalDate.now());

        int senderWallet = senderUser.getWallet();
        int amountTransaction = transactionRequest.getAmount();

        if (senderWallet > amountTransaction) {

            senderUser.setWallet(senderWallet - amountTransaction);
            receivingUser.setWallet(receivingUser.getWallet() + amountTransaction);

            userService.updateUser(senderUser);
            userService.updateUser(receivingUser);

            transactionService.addTransaction(transactionRequest);
        } else throw new SenderWalletToLowException("Solde insuffisant");

        return new ModelAndView("redirect:/homePage");


    }
}
