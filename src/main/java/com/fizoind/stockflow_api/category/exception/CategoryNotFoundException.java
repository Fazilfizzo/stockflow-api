package com.fizoind.stockflow_api.category.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException(Long category_id) {
        super("CATEGORY_NOT_FOUND", "category with id " + category_id + " not found", HttpStatus.NOT_FOUND);
    }
}
