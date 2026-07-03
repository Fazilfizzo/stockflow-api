package com.fizoind.stockflow_api.cartItem.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CartItemException extends BaseException{
    public CartItemException(String message) {
        super("CART_ITEM_EXCEPTION", message , HttpStatus.BAD_REQUEST);}
}
