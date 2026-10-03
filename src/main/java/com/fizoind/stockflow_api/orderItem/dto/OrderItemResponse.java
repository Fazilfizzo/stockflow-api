package com.fizoind.stockflow_api.orderItem.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

    public OrderItemResponse() {

    }

    public OrderItemResponse(String productId, int quantity, BigDecimal price, BigDecimal subTotal) {
        this.productName = productId;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }


}
