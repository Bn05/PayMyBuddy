package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import com.paymybuddy.paymybuddy.service.FacturationService;
import com.paymybuddy.paymybuddy.service.TransactionService;
import com.paymybuddy.paymybuddy.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransferPageController {

    final
    TransactionService transactionService;
    private final UserService userService;

    private final FacturationService facturationService;

    public TransferPageController(TransactionService transactionService, UserService userService, FacturationService facturationService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.facturationService = facturationService;
    }

    @GetMapping(value = "/transferPage")
    public String transferPage(Authentication authentication,
                               Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam(required = false, value = "walletIsToLow") boolean walletIsToLow
    ) {

        User user = userService.getCurrentUser(authentication);

        model.addAttribute("walletIsToLow", walletIsToLow);

        List<User> contacts = user.getContacts();
        model.addAttribute("contacts", contacts);

        Transaction transactionRequest = new Transaction();
        model.addAttribute("transactionRequest", transactionRequest);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<Transaction> transactionPage = transactionService.findTransactionPage(user, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionPage", transactionPage);

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "transferPage";
    }

    @GetMapping(value = "/addTransaction")
    public ModelAndView addTransaction(Authentication authentication,
                                       @RequestParam(value = "contact") int receivingUserId,
                                       @RequestParam(value = "amount") float amount,
                                       @RequestParam(value = "comment") String comment
    ) {
        User senderUser = userService.getCurrentUser(authentication);
        ModelAndView modelAndView = new ModelAndView("redirect:/transferPage");

        User receivingUser = userService.getUser(receivingUserId);

        Map<String, Double> resultMap = facturationService.addCommission(amount);
        double commissionDouble = resultMap.get("commissionRound");
        float commissionFloat = (float) commissionDouble;

        double amountMoreCommissionDouble = resultMap.get("amountMoreCommission");
        float amountMoreCommissionFloat = (float) amountMoreCommissionDouble;

        Transaction transactionRequest = new Transaction();

        transactionRequest.setSenderUser(senderUser);
        transactionRequest.setReceivingUser(receivingUser);
        transactionRequest.setTransactionDate(LocalDate.now());
        transactionRequest.setComment(comment);
        transactionRequest.setAmount(amount);
        transactionRequest.setCommission(commissionFloat);

        float senderWallet = senderUser.getWallet();
        float amountTransaction = transactionRequest.getAmount();

        if (senderWallet >= amountMoreCommissionFloat) {

            senderUser.setWallet(senderWallet - amountMoreCommissionFloat);
            receivingUser.setWallet(receivingUser.getWallet() + amountTransaction);

            User payMyBuddy = userService.getUser(1);
            payMyBuddy.setWallet(payMyBuddy.getWallet()+commissionFloat);

            userService.updateUser(senderUser);
            userService.updateUser(receivingUser);
            userService.updateUser(payMyBuddy);

            transactionService.addTransaction(transactionRequest);
        } else {
            modelAndView.addObject("walletIsToLow", true);
        }
        return modelAndView;
    }


}
