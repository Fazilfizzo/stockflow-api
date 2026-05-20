package com.fizoind.stockflow_api.customer.repository;

import com.fizoind.stockflow_api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String customerName);
}
