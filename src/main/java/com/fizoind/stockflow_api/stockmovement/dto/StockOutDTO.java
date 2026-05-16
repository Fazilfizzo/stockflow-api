package com.fizoind.stockflow_api.stockmovement.dto;

public class StockOutDTO {

    private Integer quantity;
    private String reason;
    private Long productId;

    public StockOutDTO(){}

    public StockOutDTO(Integer quantity, String reason, Long productId) {
        this.quantity = quantity;
        this.reason = reason;
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
