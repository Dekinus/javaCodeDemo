package com.example.demo.service;

import com.example.demo.exception.NotEnoughFundsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.entity.OperationType;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private ChangeBalanceRequest request;

    private static final UUID VALID_UUID = UUID.fromString("811b087f-531b-40db-8283-f308ff1ed3c5");


    @BeforeEach
    void setup() {
        request = getChangeBalanceRequest();
    }

    @Test
    void getWalletById_NotFound() {

        when(walletRepository.findById(VALID_UUID)).thenThrow(
                new ResourceNotFoundException(String.format("Wallet with id: %s was not found", VALID_UUID)));

        assertThatThrownBy(() -> walletService.changeWalletBalance(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Wallet with id: %s was not found", VALID_UUID));

        verify(walletRepository, times(1)).findById(VALID_UUID);
    }

    @Test
    void notEnoughFunds_BadRequest() {

        when(walletRepository.findById(VALID_UUID)).thenThrow(
                new NotEnoughFundsException("Wallet has not enough funds for the operation"));

        assertThatThrownBy(() -> walletService.changeWalletBalance(request))
                .isInstanceOf(NotEnoughFundsException.class)
                .hasMessage(String.format("Wallet has not enough funds for the operation", VALID_UUID));

        verify(walletRepository, times(1)).findById(VALID_UUID);
    }

    @Test
    void wrongOperationType_NotFound() {

        when(walletRepository.findById(VALID_UUID)).thenThrow(
                new ResourceNotFoundException(String.format("Operation type %s was not found", request.operationType().name())));

        assertThatThrownBy(() -> walletService.changeWalletBalance(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format(String.format("Operation type %s was not found", request.operationType().name())));

        verify(walletRepository, times(1)).findById(VALID_UUID);
    }

    private static ChangeBalanceRequest getChangeBalanceRequest() {
        return ChangeBalanceRequest.builder()
                .walletId(VALID_UUID)
                .operationType(OperationType.valueOf("DEPOSIT"))
                .amount(BigDecimal.valueOf(1000))
                .build();
    }
}
