package com.fizoind.stockflow_api.supplier.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InactiveSupplierException extends BaseException {
    public InactiveSupplierException(String message) {
        super("INACTIVE_SUPPLIER" , message,  HttpStatus.BAD_REQUEST);
    }
}
