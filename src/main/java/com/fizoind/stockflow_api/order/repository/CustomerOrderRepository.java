package com.fizoind.stockflow_api.order.repository;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerId(Long customerId);
    Optional<CustomerOrder> findByIdAndCustomerId(Long orderId, Long customerId);
    List<CustomerOrder> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
    List<CustomerOrder> findByStatus(OrderStatus status);
    Boolean existsByIdAndCustomer_User_Id(Long orderId, Long customerId);
    List<CustomerOrder> findByCustomerName(String customerName);
}
