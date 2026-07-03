package com.fizoind.stockflow_api.exception;

import org.springframework.http.HttpStatus;

public class QuantityException extends BaseException{
        public QuantityException(String message) {
            super("QUANTITY_EXCEPTION", message , HttpStatus.BAD_REQUEST);}
    }
