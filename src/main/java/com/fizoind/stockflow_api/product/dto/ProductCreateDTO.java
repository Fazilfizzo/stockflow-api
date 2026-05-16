package com.fizoind.stockflow_api.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductCreateDTO {

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "there must be description about the product")
    private String description;

    @Min(value = 1, message = "Price must be greater than zero.")
    private BigDecimal price;

    private Long supplierId;

    private Long categoryId;

    public ProductCreateDTO() {}

    public ProductCreateDTO(String name, String description,BigDecimal price, Long supplierId, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
