package com.fizoind.stockflow_api.cart.mapper;

import com.fizoind.stockflow_api.cart.dto.CartGetResponse;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.cartItem.dto.CartItemGetResponse;
import com.fizoind.stockflow_api.cartItem.dto.CartItemResponse;
import com.fizoind.stockflow_api.cartItem.entity.CartItem;

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
                        item.getQuantity(),
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

    public static CartGetResponse toGetCartResponse(Cart cart) {

        List<CartItemGetResponse> items = cart.getItems().stream()
                .map(item -> new CartItemGetResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();

        BigDecimal subTotal = items.stream()
                .map(CartItemGetResponse::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = items.stream()
                .mapToInt(CartItemGetResponse::quantity)
                .sum();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            BigDecimal lineTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        System.out.println("TOTAL: " + total);

        return new CartGetResponse(
                cart.getId(),
                items,
                subTotal,
                totalItems,
                total
        );
    }
}
