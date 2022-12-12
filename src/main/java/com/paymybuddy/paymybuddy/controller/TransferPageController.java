package com.paymybuddy.paymybuddy.controller;


import com.paymybuddy.paymybuddy.model.SecurityUser;
import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransferPageController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/transferPage")
    public String transferPage(Authentication authentication,
                               Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam(required = false, value = "walletIsToLow") boolean walletIsToLow
    ) {

        //Security Param ///
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();

        model.addAttribute("walletIsToLow", walletIsToLow);

        // End of Security Param //

        //Send Money//
        List<User> contacts = user.getContacts();
        model.addAttribute("contacts", contacts);

        Transaction transactionRequest = new Transaction();
        model.addAttribute("transactionRequest", transactionRequest);


        // END OF SEND MONEY//


        // Transaction LIST Start ////
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Transaction> transactionPage = transactionService.findTransactionPage(user,PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionPage", transactionPage);

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        //// End Of Transaction////

        return "transferPage";
    }

    @RequestMapping(value = "/addTransaction")
    public ModelAndView addTransaction(Authentication authentication,
                                       @RequestParam(value = "contact") int receivingUserId,
                                       @RequestParam(value = "amount") int amount,
                                       @RequestParam(value = "comment") String comment
    ) {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User senderUser = securityUser.getUser();
        ModelAndView modelAndView = new ModelAndView("redirect:/transferPage");

        User receivingUser = userService.getUser(receivingUserId);

        Transaction transactionRequest = new Transaction();

        transactionRequest.setSenderUser(senderUser);
        transactionRequest.setReceivingUser(receivingUser);
        transactionRequest.setTransactionDate(LocalDate.now());
        transactionRequest.setComment(comment);
        transactionRequest.setAmount(amount);

        int senderWallet = senderUser.getWallet();
        int amountTransaction = transactionRequest.getAmount();

        if (senderWallet >= amountTransaction) {

            senderUser.setWallet(senderWallet - amountTransaction);
            receivingUser.setWallet(receivingUser.getWallet() + amountTransaction);

            userService.updateUser(senderUser);
            userService.updateUser(receivingUser);

            transactionService.addTransaction(transactionRequest);
        } else {
            modelAndView.addObject("walletIsToLow", true);
        }

        return modelAndView;


    }

}
