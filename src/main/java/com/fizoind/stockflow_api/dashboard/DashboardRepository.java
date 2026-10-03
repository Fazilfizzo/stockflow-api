package com.fizoind.stockflow_api.dashboard;

import com.fizoind.stockflow_api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;


public interface DashboardRepository extends JpaRepository<Product,Long> {


    @Query("""
SELECT COUNT(p)
FROM Product p
""")
    Long countProducts();



    @Query("""
SELECT COUNT(p)
FROM Product p
WHERE p.stockQuantity <= 5
""")
    Long countLowStock();



    @Query("""
SELECT SUM(p.price * p.stockQuantity)
FROM Product p
""")
    BigDecimal calculateInventoryValue();



    @Query("""
SELECT COUNT(o)
FROM CustomerOrder o
""")
    Long countOrders();



    @Query("""
SELECT COALESCE(SUM(o.totalAmount),0)
FROM CustomerOrder o
WHERE o.status='PAID'
""")
    BigDecimal calculateRevenue();



    @Query(value = """
SELECT 
      MONTHNAME(o.created_at) AS month,
      SUM(o.total_amount) AS sales
FROM customer_orders o
WHERE o.status='PAID'
GROUP BY MONTH(o.created_at),
MONTHNAME(o.created_at)
ORDER BY MONTH(o.created_at)
""", nativeQuery = true)
    List<Object[]> salesByMonth();

}
