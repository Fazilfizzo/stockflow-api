package com.fizoind.stockflow_api.product.mapper;

import com.fizoind.stockflow_api.category.entity.Category;
import com.fizoind.stockflow_api.product.dto.ProductCreateDTO;
import com.fizoind.stockflow_api.product.dto.ProductResponseDTO;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.entity.ProductStatus;
import com.fizoind.stockflow_api.supplier.entity.Supplier;
import org.springframework.beans.factory.annotation.Value;

public class ProductMapper {
    public static Product toEntity(ProductCreateDTO productCreateDTO, Supplier supplier, Category category) {
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setSupplier(supplier);
        product.setCategory(category);
        product.setStatus(ProductStatus.ACTIVE);

        return product;
    }


    public static ProductResponseDTO toproductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setName(product.getName());
        productResponseDTO.setSku(product.getSku());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setStatus(product.getStatus());
        productResponseDTO.setImageUrl(product.getImageUrl());
//        productResponseDTO.setSupplier_name(supplier.getName());
        productResponseDTO.setSupplier_name(product.getSupplier().getName());
//        productResponseDTO.setCategory(category.getName());
        productResponseDTO.setCategory(product.getCategory().getName());
        return productResponseDTO;
    }

//
//    public static ProductResponseDTO toDTO(Product product) {
//        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
//        productResponseDTO.setName(product.getName());
//        productResponseDTO.setSku(product.getSku());
//        productResponseDTO.setDescription(product.getDescription());
//        productResponseDTO.setPrice(product.getPrice());
//        productResponseDTO.setSupplier_name(product.getSupplier().getName());
//        productResponseDTO.setCategory(product.getCategory().getName());
//        return productResponseDTO;
//    }
}
