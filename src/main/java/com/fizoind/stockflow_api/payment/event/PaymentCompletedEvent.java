package com.fizoind.stockflow_api.payment.event;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.payment.entity.Payment;

public class PaymentCompletedEvent {


    private final Payment payment;

    private final CustomerOrder order;


    public PaymentCompletedEvent(
            Payment payment,
            CustomerOrder order
    ) {
        this.payment = payment;
        this.order = order;
    }


    public Payment getPayment() {
        return payment;
    }


    public CustomerOrder getOrder() {
        return order;
    }
}