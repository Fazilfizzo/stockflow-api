package com.fizoind.stockflow_api.customer.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseException {
    public CustomerNotFoundException(Long customerId) {
        super("CATEGORY_NOT_FOUND", "customer with id " + customerId + " not found", HttpStatus.NOT_FOUND);
    }
}
