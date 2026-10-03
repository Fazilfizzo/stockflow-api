package com.fizoind.stockflow_api.dashboard;


import java.math.BigDecimal;

public class SalesChartDto {


    private String month;


    private BigDecimal sales;

    public SalesChartDto() {

    }

    public SalesChartDto(String month, BigDecimal sales) {
        this.month = month;
        this.sales = sales;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }
}
