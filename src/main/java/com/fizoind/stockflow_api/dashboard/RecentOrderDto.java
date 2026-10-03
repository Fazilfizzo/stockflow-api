package com.fizoind.stockflow_api.dashboard;


import java.math.BigDecimal;

public class RecentOrderDto {


    private String orderNumber;


    private String customer;


    private BigDecimal amount;


    private String status;

    public RecentOrderDto() {

    }

    public RecentOrderDto(String orderNumber, String customer, BigDecimal amount, String status) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.amount = amount;
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
