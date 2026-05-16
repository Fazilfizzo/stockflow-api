package com.fizoind.stockflow_api.order.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseException {
    public OrderNotFoundException(Long orderId) {
        super("ORDER_NOT_FOUND", "order with id" + orderId + "not found", HttpStatus.NOT_FOUND);
    }
}
