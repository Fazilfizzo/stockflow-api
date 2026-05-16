package com.fizoind.stockflow_api.orderItem.repository;

import com.fizoind.stockflow_api.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
