package com.fizoind.stockflow_api.payment.service;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.exception.OrderNotFoundException;
import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import com.fizoind.stockflow_api.order.service.OrderService;
import com.fizoind.stockflow_api.payment.entity.Payment;
import com.fizoind.stockflow_api.payment.entity.PaymentMethod;
import com.fizoind.stockflow_api.payment.entity.PaymentStatus;
import com.fizoind.stockflow_api.payment.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final CustomerOrderRepository customerOrderRepository;
    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;
    private final OrderService orderService;

    public PaymentService(CustomerOrderRepository customerOrderRepository, PaymentRepository paymentRepository, StripeService stripeService, OrderService orderService) {
        this.customerOrderRepository = customerOrderRepository;
        this.paymentRepository = paymentRepository;
        this.stripeService = stripeService;
        this.orderService = orderService;
    }

    @Transactional
    public String createCheckout() throws StripeException {
        String orderId = orderService.createOrderFromCart();
        CustomerOrder order = customerOrderRepository.findById(Long.valueOf(orderId)).orElseThrow(() -> new OrderNotFoundException(Long.valueOf(orderId)));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setCurrency("USD");
        payment.setPaymentMethod(PaymentMethod.STRIPE);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setTransactionReference("ORDER: " + order.getId());

        payment = paymentRepository.save(payment);

        Session session = stripeService.createCheckoutSession(order, payment);

        payment.setStripeSessionId(session.getId());

        paymentRepository.save(payment);

        return session.getUrl();
    }
}
