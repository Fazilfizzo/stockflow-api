package com.fizoind.stockflow_api.payment.repository;

import com.fizoind.stockflow_api.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByStripeSessionId(String sessionId);
}
