package com.fizoind.stockflow_api.order.mapper;

import com.fizoind.stockflow_api.customer.dto.CustomerResponseDTO;
import com.fizoind.stockflow_api.order.dto.OrderDetailsDto;
import com.fizoind.stockflow_api.order.dto.OrderResponseDTO;
import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import com.fizoind.stockflow_api.orderItem.dto.OrderItemResponse;
import com.fizoind.stockflow_api.payment.dto.PaymentResponseDto;

import java.util.List;

public class OrderMapper {

    public static OrderResponseDTO toOrderResponseDto(CustomerOrder customerOrder) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(customerOrder.getId());
        orderResponseDTO.setCustomerName(customerOrder.getCustomer().getName());
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

    public static OrderDetailsDto toOrderDetailsDto(CustomerOrder order) {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO(
                order.getCustomer().getName(),
                order.getCustomer().getPhone(),
                order.getCustomer().getEmail(),
                order.getCustomer().getAddress()
        );

        List<OrderItemResponse> itemDtos = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtOrderTime(),
                        item.getSubTotal()
                ))
                .toList();

        List<PaymentResponseDto> paymentDtos = order.getPayments()
                .stream()
                .map(payment -> new PaymentResponseDto(
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getStatus(),
                        payment.getPaymentMethod(),
                        payment.getTransactionReference()
                ))
                .toList();

        return new OrderDetailsDto(
                order.getId(),
                order.getStatus(),
                order.getOrderDate(),
                order.getTotalAmount(),
                customerResponseDTO,
                itemDtos,
                paymentDtos
        );
    }
}
