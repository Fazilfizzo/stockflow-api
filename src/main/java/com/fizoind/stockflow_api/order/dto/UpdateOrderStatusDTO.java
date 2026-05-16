package com.fizoind.stockflow_api.order.dto;

import com.fizoind.stockflow_api.order.entity.OrderStatus;

public class UpdateOrderStatusDTO {
    private OrderStatus status;

    public UpdateOrderStatusDTO() {
    }

    public UpdateOrderStatusDTO(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
