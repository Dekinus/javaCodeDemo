package com.example.demo.model.dto.request;

import com.example.demo.model.entity.OperationType;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ChangeBalanceRequest(
        UUID walletId,
        OperationType operationType,
        BigDecimal amount
) {
}
