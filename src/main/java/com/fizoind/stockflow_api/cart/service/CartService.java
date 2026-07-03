package com.fizoind.stockflow_api.cart.service;

import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartGetResponse;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.dto.UpdateCartRequest;

public interface CartService {
   CartGetResponse addToCart(AddToCartRequest addToCartRequest);
   CartGetResponse getCart();
   CartGetResponse updateCartQuantity(Long cartItemId, UpdateCartRequest updateCartRequest);
   CartGetResponse removeItem(Long cartItemId);
//   void clearCart(Long customerId);
}
