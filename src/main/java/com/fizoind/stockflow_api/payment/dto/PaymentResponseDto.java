package com.fizoind.stockflow_api.payment.dto;

import com.fizoind.stockflow_api.payment.entity.PaymentMethod;
import com.fizoind.stockflow_api.payment.entity.PaymentStatus;

import java.math.BigDecimal;

public record PaymentResponseDto(
        String paymentId,
        BigDecimal amount,
        PaymentStatus status,
        PaymentMethod paymentMethod,
        String transactionReference
) {}
