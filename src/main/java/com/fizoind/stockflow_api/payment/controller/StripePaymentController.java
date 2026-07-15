package com.fizoind.stockflow_api.payment.controller;

import com.fizoind.stockflow_api.payment.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripePaymentController {

    private final StripeService stripeService;

    public StripePaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(

            @RequestBody String payload,

            @RequestHeader("Stripe-Signature") String signature

    ) {


        stripeService.processWebhook(
                payload,
                signature
        );

        return ResponseEntity.ok("Webhook received");

    }
}
