package com.fizoind.stockflow_api.supplier.exception;

import com.fizoind.stockflow_api.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SupplierNotFoundException extends BaseException {
   public SupplierNotFoundException(Long supplier_id) {
       super("SUPPLIER_NOT_FOUND", "supplier with id" + supplier_id + " not found",  HttpStatus.NOT_FOUND);
   }
}
