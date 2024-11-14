package com.example.demo.controller;

import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.dto.response.CheckBalanceResponse;
import com.example.demo.model.dto.response.SimpleMessageResponse;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("wallet")
    public ResponseEntity<SimpleMessageResponse> updateWallet(@RequestBody ChangeBalanceRequest changeBalanceRequest) {
        return ResponseEntity.ok(walletService.changeWalletBalance(changeBalanceRequest));
    }

    @GetMapping("wallets/{walletId}")
    public CheckBalanceResponse checkWalletBalance(@PathVariable UUID walletId) {
        return walletService.checkWalletBalance(walletId);
    }
}
