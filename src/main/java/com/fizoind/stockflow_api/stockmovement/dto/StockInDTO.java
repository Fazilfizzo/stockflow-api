package com.fizoind.stockflow_api.stockmovement.dto;

public class StockInDTO {

    private Integer quantity;
    private String reason;
    private Long productId;
    private Long supplierId;

    public StockInDTO(){}

    public StockInDTO(Integer quantity, String reason, Long productId, Long supplierId) {
        this.quantity = quantity;
        this.reason = reason;
        this.productId = productId;
        this.supplierId = supplierId;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
