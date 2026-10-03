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


    public PaymentService(
            CustomerOrderRepository customerOrderRepository,
            PaymentRepository paymentRepository,
            StripeService stripeService,
            OrderService orderService
    ) {
        this.customerOrderRepository = customerOrderRepository;
        this.paymentRepository = paymentRepository;
        this.stripeService = stripeService;
        this.orderService = orderService;
    }



    @Transactional
    public String createCheckout() throws StripeException {


        // 1. Create order from cart
        Long orderId =
                Long.valueOf(
                        orderService.createOrderFromCart()
                );


        CustomerOrder order =
                customerOrderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () -> new OrderNotFoundException(orderId)
                        );



        // 2. Create pending payment
        Payment payment = new Payment();

        payment.setOrder(order);

        payment.setAmount(
                order.getTotalAmount()
        );

        payment.setCurrency(
                "USD"
        );

        payment.setPaymentMethod(
                PaymentMethod.STRIPE
        );

        payment.setStatus(
                PaymentStatus.PENDING
        );

        payment.setPaymentId(
                UUID.randomUUID().toString()
        );

        payment.setTransactionReference(
                "ORDER:" + order.getId()
        );



        payment =
                paymentRepository.save(payment);



        // 3. Create Stripe checkout session
        Session session =
                stripeService.createCheckoutSession(
                        order,
                        payment
                );



        // 4. Store Stripe session ID
        payment.setStripeSessionId(
                session.getId()
        );


        paymentRepository.save(payment);



        return session.getUrl();
    }
}