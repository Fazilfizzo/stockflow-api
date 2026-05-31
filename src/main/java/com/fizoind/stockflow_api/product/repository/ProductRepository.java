package com.fizoind.stockflow_api.product.repository;

import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
}
