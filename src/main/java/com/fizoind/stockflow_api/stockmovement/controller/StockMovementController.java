package com.fizoind.stockflow_api.stockmovement.controller;

import com.fizoind.stockflow_api.stockmovement.dto.StockInDTO;
import com.fizoind.stockflow_api.stockmovement.dto.StockMovementResponseDTO;
import com.fizoind.stockflow_api.stockmovement.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/stockMovement/in")
    public ResponseEntity<StockMovementResponseDTO> stockIn(@RequestBody StockInDTO stockInDTO) {
      return new ResponseEntity<>(stockMovementService.stockIn(stockInDTO), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/stockMovement/out")
    public ResponseEntity<StockMovementResponseDTO> stockOut(@RequestBody StockInDTO stockInDTO) {
        return new ResponseEntity<>(stockMovementService.stockOut(stockInDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stockMovement/history/{id}")
    public ResponseEntity<List<StockMovementResponseDTO>> getStockHistory(@PathVariable Long id) {
        return new ResponseEntity<>(stockMovementService.getStockHistory(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("stockMovement/current-stock/{id}")
    public ResponseEntity<String> getCurrentStock(@PathVariable Long id) {
        return new ResponseEntity<>(stockMovementService.getCurrentStock(id), HttpStatus.OK);
    }
}
