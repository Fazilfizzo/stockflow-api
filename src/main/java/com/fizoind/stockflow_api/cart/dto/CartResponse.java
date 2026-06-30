package com.fizoind.stockflow_api.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long cartId, List<com.fizoind.stockflow_api.cartItem.dto.CartItemResponse> items, BigDecimal subTotal, Integer totalItems) {
}
