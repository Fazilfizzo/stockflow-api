package com.fizoind.stockflow_api.supplier.controller;

import com.fizoind.stockflow_api.supplier.dto.StatusDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierCreateDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierResponseDTO;
import com.fizoind.stockflow_api.supplier.dto.SupplierUpdateDTO;
import com.fizoind.stockflow_api.supplier.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/supplier")
    public ResponseEntity<SupplierCreateDTO> createSupplier(@Valid @RequestBody SupplierCreateDTO supplierCreateDTO) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierCreateDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers() {
        return new ResponseEntity<>(supplierService.getAllSuppliers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("suppliers/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id) {
        return new ResponseEntity<>(supplierService.getSupplierById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("suppliers/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierUpdateDTO supplierUpdateDTO) {
        return new ResponseEntity<>(supplierService.updateSupplier(id, supplierUpdateDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("suppliers/status/{id}")
    public ResponseEntity<SupplierResponseDTO> activateSupplier(@PathVariable Long id, @RequestBody StatusDTO statusDTO) {
      return new ResponseEntity<>(supplierService.activateSupplier(id, statusDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

}

