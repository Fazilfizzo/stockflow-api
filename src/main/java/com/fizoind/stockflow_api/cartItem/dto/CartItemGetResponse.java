package com.fizoind.stockflow_api.cartItem.dto;

import java.math.BigDecimal;

public record CartItemGetResponse(Long cartItemId, Long productId, String productName, String productImage, BigDecimal unitPrice, Integer quantity, BigDecimal total) {
}
