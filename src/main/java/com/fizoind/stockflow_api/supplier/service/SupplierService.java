package com.fizoind.stockflow_api.supplier.service;

import com.fizoind.stockflow_api.supplier.dto.StatusDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierCreateDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierResponseDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierUpdateDTO;
import com.fizoind.stockflow_api.supplier.entity.Supplier;
import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;
import com.fizoind.stockflow_api.supplier.exception.SupplierNotFoundException;
import com.fizoind.stockflow_api.supplier.mapper.SupplierMapper;
import com.fizoind.stockflow_api.supplier.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);

    //    private final SupplierMapper supplierMapper;
    private final SupplierRepository supplierRepository;


    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierCreateDTO createSupplier(SupplierCreateDTO supplierCreateDTO) {
        logger.info("Starting to create supplier");
        Supplier supplier = SupplierMapper.toEntity(supplierCreateDTO);
        supplier.setStatus(SupplierStatus.INACTIVE);
        Supplier saved = supplierRepository.save(supplier);
        logger.info("Supplier created successfully, {}", saved.getName());
        return SupplierMapper.createDTO(saved);
    }

    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierMapper::supplierResponseDTO)
                .toList();
    }

    public SupplierResponseDTO getSupplierById(Long supplier_id) {
        Supplier supplier = supplierRepository.findById(supplier_id).orElseThrow(() -> new SupplierNotFoundException(supplier_id));
        logger.info("supplier fetched successfully {}", supplier.getName());
        return SupplierMapper.supplierResponseDTO(supplier);
    }

    public SupplierResponseDTO updateSupplier(Long supplier_id, SupplierUpdateDTO supplierUpdateDTO) {
        logger.info("Starting to update supplier with id {}", supplier_id);
        Supplier supplier = supplierRepository.findById(supplier_id).orElseThrow(() -> new SupplierNotFoundException(supplier_id));
        SupplierMapper.updateEntity(supplier, supplierUpdateDTO);
        Supplier updated = supplierRepository.save(supplier);
        logger.info("supplier with id {} updated successfully", supplier_id);
        return SupplierMapper.supplierResponseDTO(updated);
    }

    public SupplierResponseDTO activateSupplier(Long id, StatusDTO statusDTO) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException(id));
        if (statusDTO.getStatus().equals("ACTIVE")) {
            supplier.setStatus(SupplierStatus.ACTIVE);
        }
        else {
            throw new RuntimeException("Try again to activate, Try 'ACTIVE'");
        }
        supplierRepository.save(supplier);
        return SupplierMapper.supplierResponseDTO(supplier);
    }
    
    public void deleteSupplier(Long supplier_id) {
        Supplier supplier = supplierRepository.findById(supplier_id).orElseThrow(() -> new SupplierNotFoundException(supplier_id));
        logger.info("supplier with id {} deleted successfully", supplier_id);
        supplierRepository.deleteById(supplier_id);
    }
}

