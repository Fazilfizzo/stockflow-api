package com.fizoind.stockflow_api.cart.service;

import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartResponse;

public interface CartService {
   CartResponse addToCart(Long customerId, AddToCartRequest addToCartRequest);
//   CartResponse getCart(Long customerId);
//   void removeItem(Long customerId, Long cartItemId);
//   void clearCart(Long customerId);
}
