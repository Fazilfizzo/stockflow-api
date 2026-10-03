package com.fizoind.stockflow_api.stockmovement.dto;

public class StockMovementSummaryDto {
    private long totalMovements;
    private long stockIn;
    private long stockOut;

    public StockMovementSummaryDto() {

    }

    public StockMovementSummaryDto(long totalMovements, long stockIn, long stockOut) {
        this.totalMovements = totalMovements;
        this.stockIn = stockIn;
        this.stockOut = stockOut;
    }

    public long getTotalMovements() {
        return totalMovements;
    }

    public void setTotalMovements(long totalMovements) {
        this.totalMovements = totalMovements;
    }

    public long getStockIn() {
        return stockIn;
    }

    public void setStockIn(long stockIn) {
        this.stockIn = stockIn;
    }

    public long getStockOut() {
        return stockOut;
    }

    public void setStockOut(long stockOut) {
        this.stockOut = stockOut;
    }
}
