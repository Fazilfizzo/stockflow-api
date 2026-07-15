package com.fizoind.stockflow_api.payment.service;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.order.exception.OrderNotFoundException;
import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import com.fizoind.stockflow_api.payment.entity.Payment;
import com.fizoind.stockflow_api.payment.entity.PaymentStatus;
import com.fizoind.stockflow_api.payment.repository.PaymentRepository;
import com.fizoind.stockflow_api.stockmovement.service.StockMovementService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    @Value("${stripe.webhook-secret}")
    private String endpointSecret;

    private final PaymentRepository paymentRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final StockMovementService stockMovementService;

    public StripeService(PaymentRepository paymentRepository, CustomerOrderRepository customerOrderRepository, StockMovementService stockMovementService) {
        this.paymentRepository = paymentRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.stockMovementService = stockMovementService;
    }

    public Session createCheckoutSession(CustomerOrder order, Payment payment)
            throws StripeException {


        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .putMetadata(
                                "orderId",
                                order.getId().toString()
                        )
                        .putMetadata(
                                "paymentId",
                                payment.getId().toString()
                        )
                        .setSuccessUrl(
                                "http://localhost:5173/payment-success"
                        )
                        .setCancelUrl(
                                "http://localhost:5173/payment-cancel"
                        )
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("StockFlow order #" + order.getId())
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();


        Session session = Session.create(params);

        return Session.create(params);

    }

    public void processWebhook(
            String payload,
            String signature
    ) {
        Event event;

        try {
            event = Webhook.constructEvent(
                    payload,
                    signature,
                    endpointSecret
            );
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(
                    "Invalid Stripe signature"
            );
        }


        switch(event.getType()) {
            case "checkout.session.completed":
                handleCheckoutCompleted(event);
                break;

            case "payment_intent.payment_failed":
                handlePaymentFailed(event);
                break;

            default:
                System.out.println(
                        "Unhandled event: "
                                + event.getType()
                );
        }
    }

    private void handleCheckoutCompleted(Event event) {
        System.out.println(
                "Payment successful"
        );

        Session session = (Session) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new RuntimeException("Cannot deserialize Stripe session"));

        String orderId = session
                        .getMetadata()
                        .get("orderId");

        String paymentId = session
                        .getMetadata()
                        .get("paymentId");

        if (paymentId == null || orderId == null) {
            throw new RuntimeException("Missing payment metadata");
        }

        Payment payment = paymentRepository.findByStripeSessionId(session.getId()).orElseThrow(() -> new RuntimeException("Payment not found"));
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return;
        }
        payment.setStatus(PaymentStatus.SUCCESS);

        CustomerOrder order = customerOrderRepository.findById(Long.valueOf(orderId)).orElseThrow(() -> new OrderNotFoundException(Long.valueOf(orderId)));
        order.setStatus(OrderStatus.CONFIRMED);

        stockMovementService.reduceStock(order);
        // 1. Get Checkout Session
        // 2. Extract metadata
        // 3. Find Payment
        // 4. Update Payment status
        // 5. Confirm Order
        // 6. Reduce Stock
        // 7. Create Receipt
    }

    private void handlePaymentFailed(Event event) {
        System.out.println(
                "Payment failed"
        );

        Session session = (Session) event
                .getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new RuntimeException("Cannot deserialize Stripe session"));

        String paymentId = session
                .getMetadata()
                .get("paymentId");

        // Update payment status FAILED
        Payment payment = paymentRepository.findByStripeSessionId(session.getId()).orElseThrow(() -> new RuntimeException("Payment not found"));
}

}
