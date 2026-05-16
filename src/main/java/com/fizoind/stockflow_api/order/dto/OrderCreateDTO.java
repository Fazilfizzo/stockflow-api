package com.fizoind.stockflow_api.order.dto;

import com.fizoind.stockflow_api.orderItem.dto.OrderItemDTO;

import java.util.List;

public class OrderCreateDTO {
    List<OrderItemDTO> items;



    public OrderCreateDTO() {

    }

    public OrderCreateDTO(List<OrderItemDTO> items) {
        this.items = items;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
