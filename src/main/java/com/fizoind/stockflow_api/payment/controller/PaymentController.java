package com.fizoind.stockflow_api.payment.controller;

import com.fizoind.stockflow_api.payment.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment/checkout/{orderId}")
    public ResponseEntity<?> checkout(@PathVariable Long orderId) throws StripeException {
        String url = paymentService.createCheckout(orderId);

        return ResponseEntity.ok(
                Map.of("checkoutUrl", url)
        );
    }
}
