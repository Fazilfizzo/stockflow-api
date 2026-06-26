package com.fizoind.stockflow_api.cart.repository;

import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer(Customer customer);
}
