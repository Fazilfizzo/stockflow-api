package com.fizoind.stockflow_api.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> summary(){
        return ResponseEntity.ok(
                dashboardService.getSummary()
        );
    }


    @GetMapping("/recent-orders")
    public ResponseEntity<List<RecentOrderDto>> recentOrders(){


        return ResponseEntity.ok(
                dashboardService.getRecentOrders()
        );


    }



    @GetMapping("/sales-chart")
    public ResponseEntity<List<SalesChartDto>> salesChart(){


        return ResponseEntity.ok(
                dashboardService.getSalesChart()
        );


    }


}
