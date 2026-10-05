package com.fizoind.stockflow_api.payment.service;


import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.order.exception.OrderNotFoundException;
import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import com.fizoind.stockflow_api.payment.entity.Payment;
import com.fizoind.stockflow_api.payment.entity.PaymentStatus;
import com.fizoind.stockflow_api.payment.event.PaymentCompletedEvent;
import com.fizoind.stockflow_api.payment.repository.PaymentRepository;
import com.fizoind.stockflow_api.stockmovement.service.StockMovementService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;


@Service
public class StripeService {

    @Value("${default.origin}")
    String origin;

    private static final Logger log = LoggerFactory.getLogger(StripeService.class);


    @Value("${stripe.webhook-secret}")
    private String endpointSecret;


    private final PaymentRepository paymentRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final StockMovementService stockMovementService;
    private final ApplicationEventPublisher eventPublisher;


    public StripeService(PaymentRepository paymentRepository, CustomerOrderRepository customerOrderRepository, StockMovementService stockMovementService, ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.stockMovementService = stockMovementService;
        this.eventPublisher = eventPublisher;
    }

    public Session createCheckoutSession(CustomerOrder order, Payment payment) throws StripeException {

        log.info("starting to create checkout session");



        SessionCreateParams params = SessionCreateParams.builder()

                .setMode(SessionCreateParams.Mode.PAYMENT)

                .putMetadata("orderId", order.getId().toString())

                .putMetadata("paymentId", payment.getId().toString())

                .setSuccessUrl(origin + "/payment-success")

                .setCancelUrl(origin + "/payment-cancel")


                .addLineItem(createLineItem(order))


                .build();

        log.info("Session created successfully on return url: {}", params.getSuccessUrl());


        log.info("Stripe session parameters created successfully");

        try {
            Session session = Session.create(params);

            log.info("Stripe Checkout session created successfully: {}",
                    session.getId());

            return session;

        } catch (StripeException e) {
            log.error("Stripe Checkout session creation failed", e);
            throw e;
        }

    }


    private SessionCreateParams.LineItem createLineItem(CustomerOrder order) {

        return SessionCreateParams.LineItem.builder()

                .setQuantity(1L)

                .setPriceData(

                        SessionCreateParams.LineItem.PriceData.builder()

                                .setCurrency("usd")

                                .setUnitAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue())

                                .setProductData(

                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()

                                                .setName("StockFlow Order #" + order.getId())

                                                .build())

                                .build())

                .build();

    }


    public void processWebhook(String payload, String signature) {

        log.info("Starting to process webhook with payload: {}", payload);


        Event event;


        try {

            event = Webhook.constructEvent(payload, signature, endpointSecret);


        } catch (SignatureVerificationException e) {

            throw new RuntimeException("Invalid Stripe webhook");
        }


        switch (event.getType()) {


            case "checkout.session.completed" -> handleCheckoutCompleted(event);


            case "checkout.session.expired", "payment_intent.payment_failed" -> handlePaymentFailed(event);


            default -> log.info("Ignored Stripe event {}", event.getType());

        }

    }


    @Transactional
    protected void handleCheckoutCompleted(Event event) {


        Session session = getSession(event);


        Payment payment = paymentRepository.findByStripeSessionId(session.getId()).orElseThrow(() -> new RuntimeException("Payment not found"));


        // idempotency protection

        if (payment.getStatus() == PaymentStatus.SUCCESS) {

            log.info("Payment already processed {}", payment.getId());

            return;
        }


        String orderId = session.getMetadata().get("orderId");


        CustomerOrder order = customerOrderRepository.findById(Long.valueOf(orderId))

                .orElseThrow(() -> new OrderNotFoundException(Long.valueOf(orderId)));


        payment.setStatus(PaymentStatus.SUCCESS);


        order.setStatus(OrderStatus.PAID);


        stockMovementService.reduceStock(order);


        paymentRepository.save(payment);

        customerOrderRepository.save(order);

        eventPublisher.publishEvent(new PaymentCompletedEvent(payment, order));


        log.info("Order {} completed", order.getId());

    }


    @Transactional
    protected void handlePaymentFailed(Event event) {

        Session session = getSession(event);


        Payment payment = paymentRepository.findByStripeSessionId(session.getId())

                .orElseThrow(() -> new RuntimeException("Payment not found"));


        payment.setStatus(PaymentStatus.FAILED);


        paymentRepository.save(payment);


        log.info("Payment failed {}", payment.getId());

    }


    private Session getSession(Event event) {


        return (Session)

                event.getDataObjectDeserializer()

                        .getObject()

                        .orElseThrow(() -> new RuntimeException("Cannot deserialize Stripe session"));

    }


}