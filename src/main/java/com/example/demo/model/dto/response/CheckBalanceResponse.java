package com.example.demo.model.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CheckBalanceResponse(
        BigDecimal amount
) {
}
