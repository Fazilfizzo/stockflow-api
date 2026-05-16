package com.fizoind.stockflow_api.stockmovement.repository;

import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    @Query("""
            SELECT COALESCE(SUM(
            CASE
            WHEN s.movementType = 'IN' THEN s.quantity
            WHEN s.movementType = 'OUT' THEN -s.quantity
            END
            ),0)
            FROM StockMovement s
            WHERE s.product.id = :productId
            """)
    Integer getCurrentStock(Long productId);

    List<StockMovement> findByProductId(Long productId);
    List<StockMovement> findByProductIdOrderByMovementDateDesc(Long productId);
}
