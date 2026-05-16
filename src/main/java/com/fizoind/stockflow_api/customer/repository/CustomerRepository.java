package com.fizoind.stockflow_api.customer.repository;

import com.fizoind.stockflow_api.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
