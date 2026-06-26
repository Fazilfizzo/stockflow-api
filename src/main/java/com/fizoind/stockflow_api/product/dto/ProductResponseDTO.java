package com.fizoind.stockflow_api.product.dto;

import com.fizoind.stockflow_api.product.entity.ProductStatus;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private String description;
    private ProductStatus status;
    private String imageUrl;
    private String supplier_name;
    private String category;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, String sku, BigDecimal price, String description, ProductStatus status, String imageUrl, String supplier_name, String category) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.description = description;
        this.status = status;
        this.supplier_name = supplier_name;
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
