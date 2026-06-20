package com.fizoind.stockflow_api.supplier.mapper;

import com.fizoind.stockflow_api.supplier.dto.SupplierCreateDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierResponseDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierUpdateDTO;
import com.fizoind.stockflow_api.supplier.entity.Supplier;

public class SupplierMapper {
    public static Supplier toEntity(SupplierCreateDTO supplierCreateDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierCreateDTO.getName());
        supplier.setEmail(supplierCreateDTO.getEmail());
        supplier.setPhone(supplierCreateDTO.getPhone());
        supplier.setAddress(supplierCreateDTO.getAddress());
        return supplier;
    }

    public static SupplierResponseDTO supplierResponseDTO(Supplier supplier) {
        SupplierResponseDTO supplierResponseDTO = new SupplierResponseDTO();
        supplierResponseDTO.setId(supplier.getId());
        supplierResponseDTO.setName(supplier.getName());
        supplierResponseDTO.setEmail(supplier.getEmail());
        supplierResponseDTO.setPhone(supplier.getPhone());
        supplierResponseDTO.setAddress(supplier.getAddress());
        supplierResponseDTO.setStatus(supplier.getStatus());
        return supplierResponseDTO;
    }

    public static void updateEntity(Supplier supplier, SupplierUpdateDTO supplierUpdateDTO) {
        supplier.setName(supplierUpdateDTO.getName());
        supplier.setEmail(supplierUpdateDTO.getEmail());
        supplier.setPhone(supplierUpdateDTO.getPhone());
        supplier.setAddress(supplierUpdateDTO.getAddress());
    }

    public static SupplierCreateDTO createDTO(Supplier supplier) {
        SupplierCreateDTO supplierCreateDTO = new SupplierCreateDTO();
        supplierCreateDTO.setName(supplier.getName());
        supplierCreateDTO.setEmail(supplier.getEmail());
        supplierCreateDTO.setPhone(supplier.getPhone());
        supplierCreateDTO.setAddress(supplier.getAddress());
        return supplierCreateDTO;
    }
}

