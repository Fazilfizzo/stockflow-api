package com.fizoind.stockflow_api.product.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InactiveProductException extends BaseException {
    public InactiveProductException(String message) {
        super("INACTIVE_PRODUCT", message, HttpStatus.BAD_REQUEST);
    }
}
