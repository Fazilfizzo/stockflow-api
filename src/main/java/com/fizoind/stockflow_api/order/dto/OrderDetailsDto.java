package com.fizoind.stockflow_api.order.dto;

import com.fizoind.stockflow_api.customer.dto.CustomerResponseDTO;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemResponse;
import com.fizoind.stockflow_api.payment.dto.PaymentResponseDto;
import com.fizoind.stockflow_api.payment.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailsDto(
        Long id,
        OrderStatus status,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        CustomerResponseDTO customer,
        List<OrderItemResponse> items,
        List<PaymentResponseDto> payments
) {}
