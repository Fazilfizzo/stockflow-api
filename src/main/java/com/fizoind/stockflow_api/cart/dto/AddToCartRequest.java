package com.fizoind.stockflow_api.cart.dto;

public record AddToCartRequest(Long productId, Integer quantity) {
}
