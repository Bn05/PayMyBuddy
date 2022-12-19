package com.paymybuddy.paymybuddy.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface FacturationService {

    public Map<String, Double> getCommission(float amountTransaction);


}
