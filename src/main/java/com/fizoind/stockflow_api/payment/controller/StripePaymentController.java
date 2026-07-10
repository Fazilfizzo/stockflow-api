package com.fizoind.stockflow_api.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe")
public class StripePaymentController {

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestBody String payload) {
        return ResponseEntity.ok("Webhook received.");
    }
}
