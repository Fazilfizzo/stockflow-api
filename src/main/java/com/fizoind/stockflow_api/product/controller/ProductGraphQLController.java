package com.fizoind.stockflow_api.product.controller;

import com.fizoind.stockflow_api.product.dto.ProductCreateDTO;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.product.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductGraphQLController {
      private final ProductService productService;
      private final ProductRepository productRepository;

    public ProductGraphQLController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @QueryMapping
    public List<Product> products() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product product(@Argument Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
}
