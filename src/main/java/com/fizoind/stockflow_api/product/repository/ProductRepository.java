package com.fizoind.stockflow_api.product.repository;

import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    int countByCategory(Category category);
}
