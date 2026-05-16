package com.fizoind.stockflow_api.stockmovement.dto;

import com.fizoind.stockflow_api.stockmovement.entity.MovementType;

import java.time.LocalDateTime;

public class StockMovementResponseDTO {
    private Long id;
    private Integer quantity;
    private MovementType movementType;
    private String reason;
    private LocalDateTime movementDate;
    private String product_name;

    public StockMovementResponseDTO(){}

    public StockMovementResponseDTO(Long id, Integer quantity, MovementType movementType, String reason, LocalDateTime movementDate, String product_name) {
        this.id = id;
        this.quantity = quantity;
        this.movementType = movementType;
        this.reason = reason;
        this.movementDate = movementDate;
        this.product_name = product_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
