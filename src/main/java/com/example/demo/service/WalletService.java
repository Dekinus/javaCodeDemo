package com.example.demo.service;

import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.dto.response.CheckBalanceResponse;
import com.example.demo.model.dto.response.SimpleMessageResponse;

import java.util.UUID;

public interface WalletService {
    SimpleMessageResponse changeWalletBalance(ChangeBalanceRequest changeBalanceRequest);

    CheckBalanceResponse checkWalletBalance(UUID walletId);
}
