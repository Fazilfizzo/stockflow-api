package com.fizoind.stockflow_api.payment.entity;


import com.fizoind.stockflow_api.auditing.Auditable;
import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class Payment extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String paymentId;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;


    private BigDecimal amount;


    private String currency;


    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


    private String stripeSessionId;


    private String stripePaymentIntentId;


    private String transactionReference;


    public Payment() {

    }

    public Payment(Long id, String paymentId, CustomerOrder order, BigDecimal amount, String currency, PaymentStatus status, PaymentMethod paymentMethod, String stripeSessionId, String stripePaymentIntentId, String transactionReference) {
        this.id = id;
        this.paymentId = paymentId;
        this.order = order;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.stripeSessionId = stripeSessionId;
        this.stripePaymentIntentId = stripePaymentIntentId;
        this.transactionReference = transactionReference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentId='" + paymentId + '\'' +
                ", order=" + order +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", paymentMethod=" + paymentMethod +
                ", stripeSessionId='" + stripeSessionId + '\'' +
                ", stripePaymentIntentId='" + stripePaymentIntentId + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                '}';
    }
}
