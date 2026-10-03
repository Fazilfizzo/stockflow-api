package com.fizoind.stockflow_api.product.repository;

import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    int countByCategory(Category category);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Product p
            SET p.stockQuantity = p.stockQuantity - :quantity
            WHERE p.id = :productId
            AND p.stockQuantity >= :quantity                                    
         """)
    int reduceStock(@Param("productId") Long productId, @Param("quantity") int quantity);

    @Query("SELECT p FROM Product p")
    List<Product> getAllProducts();

    // select c.name, o.total_amount from customers c join customer_orders o on o.customer_id = c.id where o.total_amount > 98000;
    @Query("""
SELECT p.name
FROM Product p
WHERE p.stockQuantity < 10
""")
    List<String> getLowStockProducts();

    @Query("""
        SELECT p
        FROM Product p
        WHERE LOWER(p.name) LIKE
        LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Product> search(@Param("keyword") String keyword, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    Optional<Product> findByName(String name);
}
