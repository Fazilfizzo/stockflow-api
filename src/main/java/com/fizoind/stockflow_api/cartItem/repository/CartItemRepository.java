package com.fizoind.stockflow_api.cartItem.repository;

import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.cartItem.entity.CartItem;
import com.fizoind.stockflow_api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
