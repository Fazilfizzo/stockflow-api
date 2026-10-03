package com.fizoind.stockflow_api.order.dto;

import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Integer numberOfOrderItems;
    private List<OrderItemResponse> items;

    public OrderResponseDTO() {

    }

    public OrderResponseDTO(Long orderId, String customerName, LocalDateTime orderDate, BigDecimal totalAmount, OrderStatus orderStatus, Integer numberOfOrderItems, List<OrderItemResponse> items) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.numberOfOrderItems = numberOfOrderItems;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

//    public List<OrderItemResponse> getItems() {
//        return items;
//    }
//
//    public void setItems(List<OrderItemResponse> items) {
//        this.items = items;
//    }


    public Integer getNumberOfOrderItems() {
        return numberOfOrderItems;
    }

    public void setNumberOfOrderItems(Integer numberOfOrderItems) {
        this.numberOfOrderItems = numberOfOrderItems;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
