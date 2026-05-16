package com.fizoind.stockflow_api.stockmovement.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends BaseException {
    public InsufficientStockException(Long productId) {
        super("INSUFFICIENT_STOCK", "Insufficient stock of productId " + productId , HttpStatus.NOT_FOUND);
    }
}
