package com.fizoind.stockflow_api.exception;

import com.fizoind.stockflow_api.common.ApiResponse;
import com.fizoind.stockflow_api.common.ResponseUtil;
import com.fizoind.stockflow_api.customer.exception.CustomerNotFoundException;
import com.fizoind.stockflow_api.product.exception.InactiveProductException;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.stockmovement.exception.InsufficientStockException;
import com.fizoind.stockflow_api.supplier.exception.InactiveSupplierException;
import com.fizoind.stockflow_api.supplier.exception.SupplierNotFoundException;
import com.fizoind.stockflow_api.user.UsernameNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleSupplierNotFoundException(HttpServletRequest request, SupplierNotFoundException supplierNotFoundException) {
        List<String> errors = Arrays.asList(supplierNotFoundException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Supplier does not exist", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, ProductNotFoundException productNotFoundException) {
        List<String> errors = Arrays.asList(productNotFoundException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Product does not exist", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, CustomerNotFoundException customerNotFoundException) {
        List<String> errors = Arrays.asList(customerNotFoundException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Customer does not exist", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, InsufficientStockException insufficientStockException) {
        List<String> errors = Arrays.asList(insufficientStockException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Insufficient stock", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InactiveProductException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, InactiveProductException inactiveProductException) {
        List<String> errors = Arrays.asList(inactiveProductException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Product is INACTIVE", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

@ExceptionHandler(InactiveSupplierException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, InactiveSupplierException inactiveSupplierException) {
        List<String> errors = Arrays.asList(inactiveSupplierException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Supplier is INACTIVE", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePublicNotFoundException(HttpServletRequest request, UsernameNotFoundException usernameNotFoundException) {
        List<String> errors = Arrays.asList(usernameNotFoundException.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Supplier is INACTIVE", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(HttpServletRequest request, Exception exception) {
        List<String> errors = Arrays.asList(exception.getMessage());
        ApiResponse<Void> response = ResponseUtil.error(errors, "Unexpected exception occurred.", 404, request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
