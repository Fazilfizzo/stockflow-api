package com.fizoind.stockflow_api.cartItem.dto;

import java.math.BigDecimal;

public record CartItemResponse(Long cartItemId, Long productId, String productName, BigDecimal price, Integer quantity, BigDecimal total) {
}
