package com.fizoind.stockflow_api.dashboard;


import java.math.BigDecimal;


public class DashboardSummaryDto {


    private Long totalProducts;


    private Long totalOrders;


    private BigDecimal totalRevenue;


    private Long lowStockProducts;


    private BigDecimal inventoryValue;

    public DashboardSummaryDto() {

    }

    public DashboardSummaryDto(Long totalProducts, Long totalOrders, BigDecimal totalRevenue, Long lowStockProducts, BigDecimal inventoryValue) {
        this.totalProducts = totalProducts;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.lowStockProducts = lowStockProducts;
        this.inventoryValue = inventoryValue;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(Long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public BigDecimal getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(BigDecimal inventoryValue) {
        this.inventoryValue = inventoryValue;
    }
}
