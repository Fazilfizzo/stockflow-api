package com.fizoind.stockflow_api.cart.dto;

import com.fizoind.stockflow_api.cartItem.dto.CartItemGetResponse;

import java.math.BigDecimal;
import java.util.List;

public record CartGetResponse(Long cartId, List<CartItemGetResponse> items, BigDecimal subTotal, Integer totalItems) {
}
