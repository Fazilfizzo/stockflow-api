package com.fizoind.stockflow_api.order.controller;

import com.fizoind.stockflow_api.order.dto.OrderCreateDTO;
import com.fizoind.stockflow_api.order.dto.OrderResponseDTO;
import com.fizoind.stockflow_api.order.dto.UpdateOrderStatusDTO;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import com.fizoind.stockflow_api.order.service.OrderService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerOrderController {

    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<String> createOrder() {
        return new ResponseEntity<>(orderService.createOrderFromCart(), HttpStatusCode.valueOf(201));
    }


    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getCustomerOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getCustomerOrder(orderId), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/orders/customers")
    public ResponseEntity<List<OrderResponseDTO>> getAllCustomerOrders() {
        return new ResponseEntity<>(orderService.getCustomerOrders(), HttpStatusCode.valueOf(200));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatusCode.valueOf(200));
    }


    @GetMapping("/orders/{customerId}/status")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersOfCustomerByStatus(@PathVariable Long customerId, @RequestParam OrderStatus status) {
        return new ResponseEntity<>(orderService.getCustomerOrdersByStatus(customerId, status), HttpStatusCode.valueOf(200));
    }


    @GetMapping("/orders/status")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(@RequestParam OrderStatus status) {
        return new ResponseEntity<>(orderService.getOrdersByStatus(status), HttpStatusCode.valueOf(200));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId, updateOrderStatusDTO.getStatus()), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>("ORDER CANCELLED SUCCESSFULLY!!!!!!!!", HttpStatusCode.valueOf(200));
    }
}
