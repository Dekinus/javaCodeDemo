package com.example.demo.controller;

import com.example.demo.exception.NotEnoughFundsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.request.ChangeBalanceRequest;
import com.example.demo.model.dto.response.SimpleMessageResponse;
import com.example.demo.model.entity.OperationType;
import com.example.demo.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    private ChangeBalanceRequest request;

    private static final UUID VALID_UUID = UUID.fromString("811b087f-531b-40db-8283-f308ff1ed3c5");

    @BeforeEach
    void setup() {
        request = getChangeBalanceRequest();
    }

    @Test
    @DisplayName("Should return status 200 wallet update success")
    void updateWallet_shouldReturnHttp200() throws Exception {

        Mockito.when(walletService.changeWalletBalance(request))
                .thenReturn(new SimpleMessageResponse("Wallet balance was updated successfully"));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Wallet balance was updated successfully"));
    }

    @Test
    @DisplayName("Should return status 400 Bad Request")
    void updateWallet_shouldReturnHttp400() throws Exception {

        Mockito.when(walletService.changeWalletBalance(request))
                .thenThrow(new NotEnoughFundsException("Wallet has not enough funds for the operation"));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Wallet has not enough funds for the operation"));
    }

    @Test
    @DisplayName("Should return status 404 Not Found")
    void updateWallet_shouldReturnHttp404() throws Exception {

        Mockito.when(walletService.changeWalletBalance(request))
                .thenThrow(new ResourceNotFoundException(String.format("Wallet with id: %s was not found", getChangeBalanceRequest().walletId())));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(String.format("Wallet with id: %s was not found", getChangeBalanceRequest().walletId())));
    }

    private static ChangeBalanceRequest getChangeBalanceRequest() {
        return ChangeBalanceRequest.builder()
                .walletId(VALID_UUID)
                .operationType(OperationType.valueOf("DEPOSIT"))
                .amount(BigDecimal.valueOf(1000))
                .build();
    }
}
