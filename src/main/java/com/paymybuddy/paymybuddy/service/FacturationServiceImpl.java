package com.paymybuddy.paymybuddy.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

@Service
public class FacturationServiceImpl implements FacturationService {

@Override
    public Map<String, Double> getCommission(float amountTransaction) {

        double commissionPerCent = 5.00;
        double commission = amountTransaction * ((commissionPerCent) / 100);
        double commissionRound = round(commission * 100.00) / 100.00;
        double amountLessCommission = amountTransaction - commissionRound;

        Map<String, Double> resultMap = new HashMap<>();

        resultMap.put("commissionPerCent", commissionPerCent);
        resultMap.put("commissionRound", commissionRound);
        resultMap.put("amountLessCommission", amountLessCommission);

        return resultMap;
    }
}
