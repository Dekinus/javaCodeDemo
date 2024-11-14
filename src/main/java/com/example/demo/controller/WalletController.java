package com.example.demo.controller;

import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.dto.response.CheckBalanceResponse;
import com.example.demo.model.dto.response.SimpleMessageResponse;
import com.example.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }
}

