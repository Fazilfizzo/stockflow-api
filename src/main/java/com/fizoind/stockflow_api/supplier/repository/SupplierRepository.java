package com.fizoind.stockflow_api.supplier.repository;

import com.fizoind.stockflow_api.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByIdempotencyKey(String idempotencyKey);

    Optional<Supplier> findByIdempotencyKey(String idempotencyKey);
}
