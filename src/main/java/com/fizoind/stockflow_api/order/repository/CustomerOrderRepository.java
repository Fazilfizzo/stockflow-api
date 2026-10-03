package com.fizoind.stockflow_api.order.repository;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("SELECT o FROM CustomerOrder o WHERE o.customer.id = :customerId ORDER BY o.createdAt DESC")
    List<CustomerOrder> findByCustomerId(@Param("customerId") Long customerId);

    Optional<CustomerOrder> findByIdAndCustomerId(Long orderId, Long customerId);
    List<CustomerOrder> findByCustomerIdAndStatus(Long customerId, OrderStatus status);
    List<CustomerOrder> findByStatus(OrderStatus status);
    Boolean existsByIdAndCustomer_User_Id(Long orderId, Long customerId);
    List<CustomerOrder> findByCustomerName(String customerName);

    @Query("SELECT o FROM CustomerOrder o")
    List<CustomerOrder> getAllOrders();

    List<CustomerOrder> findTop5ByOrderByCreatedAtDesc();

    List<CustomerOrder> findAllByOrderByCreatedAtDesc();

    @Query("""
SELECT COALESCE(SUM(oi.quantity), 0)
FROM OrderItem oi
WHERE oi.order.id = :orderId
""")
    Integer getNumberOfItems(@Param("orderId") Long orderId);
}
