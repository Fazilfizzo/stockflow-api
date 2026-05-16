package com.fizoind.stockflow_api.stockmovement.mapper;

import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.stockmovement.dto.StockInDTO;
import com.fizoind.stockflow_api.stockmovement.dto.StockMovementResponseDTO;
import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;

public class StockMovementMapper {
    public static StockMovement toEntity(StockInDTO stockInDTO, Product product) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setReason(stockInDTO.getReason());
        stockMovement.setProduct(product);
        return stockMovement;
    }

    public static StockMovementResponseDTO toResponseDTO(StockMovement stockMovement) {
        StockMovementResponseDTO stockMovementResponseDTO = new StockMovementResponseDTO();
        stockMovementResponseDTO.setId(stockMovement.getId());
        stockMovementResponseDTO.setQuantity(stockMovement.getQuantity());
        stockMovementResponseDTO.setMovementType(stockMovement.getMovementType());
        stockMovementResponseDTO.setReason(stockMovement.getReason());
        stockMovementResponseDTO.setMovementDate(stockMovement.getMovementDate());
        stockMovementResponseDTO.setProduct_name(stockMovement.getProduct().getName());
        return stockMovementResponseDTO;
    }
}
