package com.fizoind.stockflow_api.cart.dto;

import com.fizoind.stockflow_api.cartItem.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long cartId, List<CartItem> items, BigDecimal subTotal, Integer totalItems) {
}
