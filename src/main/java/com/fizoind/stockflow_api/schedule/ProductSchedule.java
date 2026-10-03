package com.fizoind.stockflow_api.schedule;

import com.fizoind.stockflow_api.product.dto.ProductResponseDTO;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.mapper.ProductMapper;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.product.service.ProductService;
import com.fizoind.stockflow_api.stockmovement.service.StockMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSchedule {

    private static final Logger log = LoggerFactory.getLogger(ProductSchedule.class);

    private final ProductService productService;

    public ProductSchedule(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 10000)
    public void checkStock() {
       productService.checkStock();
    }
}
