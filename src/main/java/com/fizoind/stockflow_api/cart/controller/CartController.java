package com.fizoind.stockflow_api.cart.controller;

import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartGetResponse;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.service.impl.CartServiceImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartResponse> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        return new ResponseEntity<>(cartService.addToCart(addToCartRequest), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/cart")
    public ResponseEntity<CartGetResponse> getCart() {
        return new ResponseEntity<>(cartService.getCart(), HttpStatusCode.valueOf(200));
    }

}
