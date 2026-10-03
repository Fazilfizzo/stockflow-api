package com.fizoind.stockflow_api.stockmovement.repository;

import com.fizoind.stockflow_api.stockmovement.dto.StockMovementResponseDTO;
import com.fizoind.stockflow_api.stockmovement.entity.MovementType;
import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query("""
      SELECT sm
      FROM StockMovement sm
      JOIN sm.product p
      LEFT JOIN sm.supplier s
      WHERE 
          :search IS NULL OR :search = '' OR LOWER(p.name) LIKE
      LOWER(CONCAT('%',:search,'%'))
      AND 
          :type IS NULL OR sm.movementType = :type
      ORDER BY sm.movementDate DESC
    """)
    List<StockMovement> findAllMovements(
            @Param("search") String search,
            @Param("type")MovementType type
            );


    @Query("""
      SELECT sm
      FROM StockMovement sm
      JOIN sm.product 
      LEFT JOIN sm.supplier
      WHERE sm.id=:id
""")
    Optional<StockMovement> findMovementDetails(Long id);

    @Query("""
     SELECT COALESCE(SUM(sm.quantity), 0)
     FROM StockMovement sm
     WHERE sm.movementType = :type
""")
    long getTotalQuantityByMovementType(@Param("type") MovementType type);

    List<StockMovement> findByProductIdOrderByMovementDateDesc(Long productId);

    List<StockMovement> findAllByOrderByCreatedAtDesc();
}
