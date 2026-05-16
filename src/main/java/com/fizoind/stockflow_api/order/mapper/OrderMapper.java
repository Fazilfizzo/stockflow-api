package com.fizoind.stockflow_api.order.mapper;

import com.fizoind.stockflow_api.order.dto.OrderResponseDTO;
import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemResponse;

import java.util.List;

public class OrderMapper {
    public static OrderResponseDTO toOrderResponseDto(CustomerOrder customerOrder) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(customerOrder.getId());
        orderResponseDTO.setOrderStatus(customerOrder.getStatus());
        orderResponseDTO.setTotalAmount(customerOrder.getTotalAmount());
        orderResponseDTO.setOrderDate(customerOrder.getOrderDate());

        List<OrderItemResponse> itemDtos = customerOrder.getOrderItems().stream()
                .map(item -> {
                   OrderItemResponse  orderItemResponse = new OrderItemResponse();
                   orderItemResponse.setProductName(item.getProduct().getName());
                   orderItemResponse.setQuantity(item.getQuantity());
                   orderItemResponse.setPrice(item.getPriceAtOrderTime());
                   orderItemResponse.setSubTotal(item.getSubTotal());
                   return orderItemResponse;
                })
                .toList();

        orderResponseDTO.setItems(itemDtos);

        return orderResponseDTO;
    }
}
