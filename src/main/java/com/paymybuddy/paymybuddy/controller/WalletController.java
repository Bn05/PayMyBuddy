package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.exceptions.*;
import com.paymybuddy.paymybuddy.service.BankServive;
import com.paymybuddy.paymybuddy.service.UserService;
import com.paymybuddy.paymybuddy.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;

@RestController
public class WalletController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    BankServive bankServive;


    @PostMapping(value = "/addMoneyTowallet")
    public int addMoneyToWallet(int userId, int amountTransaction, String informationVisa) {

        if (userService.findUserById(userId) == null) {
            throw new UserNoExistException("L'utilisateur n'existe pas !");
        }
        if (amountTransaction < 0) {
            throw new IllegalAmountException("Montant negatif impossible");
        }

        boolean bankAgreement = bankServive.bankValidation(informationVisa);

        if (bankAgreement) {
            return walletService.addMoneyToWallet(userId, amountTransaction);
        } else throw new NoPermissionBankException("Refus de la banque, ou pas de réponse");
    }


    @PostMapping(value = "/transfertMoneyToBank")
    public int transfertMoneyToBank(int userId, int amountTransaction, String Iban) {

        if (userService.findUserById(userId) == null) {
            throw new UserNoExistException("L'utilisateur n'existe pas !");
        }
        if (amountTransaction < 0) {
            throw new IllegalAmountException("Montant negatif impossible");
        }

        boolean bankAgreement = bankServive.bankValidation(Iban);
        if (bankAgreement) {
            int userWallet = walletService.getWallet(userId);
            if (userWallet > amountTransaction) {
                return walletService.transfertMoneyToBank(userId, amountTransaction);
            } else throw new SenderWalletToLowException("Votre solde est trop bas");
        } else throw new NoPermissionBankException("La banque ne valide pas cet Iban");
    }
}
