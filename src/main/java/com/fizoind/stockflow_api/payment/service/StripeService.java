package com.fizoind.stockflow_api.payment.service;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {


    public Session createCheckoutSession(CustomerOrder order)
            throws StripeException {


        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
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

}
