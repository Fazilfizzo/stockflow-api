package com.fizoind.stockflow_api.supplier.repository;

import com.fizoind.stockflow_api.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
