package com.paymybuddy.paymybuddy.service;

import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepositoryMock;

    @Test
    void addTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepositoryMock.save(any())).thenReturn(transaction);

        transactionService.addTransaction(transaction);

        verify(transactionRepositoryMock,times(1)).save(transaction);

    }

    @Test
    void findTransactionPage() {




    }
}