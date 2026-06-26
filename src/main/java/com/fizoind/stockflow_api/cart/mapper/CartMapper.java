package com.fizoind.stockflow_api.cart.mapper;

import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.cartItem.dto.CartItemResponse;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartResponse toCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getProduct().getStockQuantity(),
                        item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();

        BigDecimal subTotal = items.stream()
                .map(CartItemResponse::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = items.stream()
                .mapToInt(CartItemResponse::quantity)
                .sum();

        return new CartResponse(
                cart.getId(),
                items,
                subTotal,
                totalItems
        );
    }
}
