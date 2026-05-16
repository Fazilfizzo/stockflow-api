package com.fizoind.stockflow_api.user;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(String message) {
        super("USERNAME_NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }
}
