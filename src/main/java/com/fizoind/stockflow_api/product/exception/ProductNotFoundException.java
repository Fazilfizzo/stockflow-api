package com.fizoind.stockflow_api.product.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(Long product_id) {
        super("PRODUCT_NOT_FOUND", "product with id " + product_id + " not found", HttpStatus.NOT_FOUND);
    }
}
