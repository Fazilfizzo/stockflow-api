package com.fizoind.stockflow_api.dashboard;

import com.fizoind.stockflow_api.order.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final CustomerOrderRepository orderRepository;


    public DashboardService(DashboardRepository dashboardRepository, CustomerOrderRepository orderRepository) {
        this.dashboardRepository = dashboardRepository;
        this.orderRepository = orderRepository;
    }

    public DashboardSummaryDto getSummary(){


        return new DashboardSummaryDto(

                dashboardRepository.countProducts(),

                dashboardRepository.countOrders(),

                dashboardRepository.calculateRevenue(),

                dashboardRepository.countLowStock(),

                dashboardRepository.calculateInventoryValue()

        );


    }




    public List<RecentOrderDto> getRecentOrders(){


        return orderRepository
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(order -> new RecentOrderDto(

                        order.getId().toString(),

                        order.getCustomer().getName(),

                        order.getTotalAmount(),

                        order.getStatus().name()

                ))
                .toList();


    }

    public List<SalesChartDto> getSalesChart(){


        return dashboardRepository.salesByMonth()
                .stream()
                .map(row -> new SalesChartDto(

                        (String)row[0],

                        (BigDecimal)row[1]

                ))
                .toList();
    }


}