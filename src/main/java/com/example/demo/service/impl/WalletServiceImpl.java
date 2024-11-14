package com.example.demo.service.impl;

import com.example.demo.exception.NotEnoughFundsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.dto.response.CheckBalanceResponse;
import com.example.demo.model.dto.response.SimpleMessageResponse;
import com.example.demo.model.entity.Wallet;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public SimpleMessageResponse changeWalletBalance(ChangeBalanceRequest changeBalanceRequest) {

        Wallet wallet = walletRepository.findById(changeBalanceRequest.walletId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Wallet with id: %s was not found", changeBalanceRequest.walletId())));

        switch (changeBalanceRequest.operationType()) {

            case DEPOSIT -> wallet.setBalance(wallet.getBalance().add(changeBalanceRequest.amount()));

            case WITHDRAW -> {
                if (validateSufficientFunds(wallet, changeBalanceRequest.amount())) {
                    wallet.setBalance(wallet.getBalance().subtract(changeBalanceRequest.amount()));
                }
                else {
                    throw new NotEnoughFundsException("Wallet has not enough funds for the operation");
                }
            }

            default -> throw new ResourceNotFoundException(String.format("Operation type %s was not found", changeBalanceRequest.operationType().name()));
        }

        walletRepository.save(wallet);
        return new SimpleMessageResponse("Wallet balance was updated successfully");
    }

    @Override
    public CheckBalanceResponse checkWalletBalance(UUID walletId) {
        return new CheckBalanceResponse(
                walletRepository.findById(walletId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Wallet with id: %s was not found", walletId)))
                        .getBalance());
    }

    private boolean validateSufficientFunds(Wallet wallet, BigDecimal amount) {
        return wallet.getBalance().compareTo(amount) >= 0;

    }
}
