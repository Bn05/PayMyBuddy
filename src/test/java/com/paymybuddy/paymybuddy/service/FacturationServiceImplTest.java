package com.paymybuddy.paymybuddy.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FacturationServiceImplTest {

    @InjectMocks
    FacturationServiceImpl facturationService;

    @Test
    void getCommission100Euro() {
        float amount = 100;

        Map<String, Double> resultMap = facturationService.getCommission(amount);

        assertEquals(5.0, resultMap.get("commissionPerCent"));
        assertEquals(5.0, resultMap.get("commissionRound"));
        assertEquals(95.0, resultMap.get("amountLessCommission"));

    }

    @Test
    void getCommission13Euro() {
        float amount = 13;

        Map<String, Double> resultMap = facturationService.getCommission(amount);

        assertEquals(5.0, resultMap.get("commissionPerCent"));
        assertEquals(0.65, resultMap.get("commissionRound"));
        assertEquals(12.35, resultMap.get("amountLessCommission"));

    }

    @Test
    void addCommission(){

        float amount = 100;

        Map<String, Double> resultMap = facturationService.addCommission(amount);

        assertEquals(5.0, resultMap.get("commissionPerCent"));
        assertEquals(5.0, resultMap.get("commissionRound"));
        assertEquals(105.0, resultMap.get("amountMoreCommission"));
    }
}