package com.fizoind.stockflow_api.cart.controller;

import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartGetResponse;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.dto.UpdateCartRequest;
import com.fizoind.stockflow_api.cart.service.impl.CartServiceImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/items")
    public ResponseEntity<CartGetResponse> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
        return new ResponseEntity<>(cartService.addToCart(addToCartRequest), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/cart")
    public ResponseEntity<CartGetResponse> getCart() {
        return new ResponseEntity<>(cartService.getCart(), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/cart/items/{cartItemId}")
    public ResponseEntity<CartGetResponse> updateCartQuantity(@PathVariable Long cartItemId, @RequestBody UpdateCartRequest updateCartRequest) {
        return new ResponseEntity<>(cartService.updateCartQuantity(cartItemId, updateCartRequest), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<CartGetResponse> removeCartItem(@PathVariable Long cartItemId) {
        return new ResponseEntity<>(cartService.removeItem(cartItemId), HttpStatusCode.valueOf(200));
    }
}
