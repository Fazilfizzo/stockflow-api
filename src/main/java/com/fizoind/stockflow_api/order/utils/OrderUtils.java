package com.fizoind.stockflow_api.order.utils;

import com.fizoind.stockflow_api.order.entity.OrderStatus;

public class OrderUtils {
    public static boolean isValiTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PENDING -> next == OrderStatus.PAID || next == OrderStatus.CANCELLED;
            case PAID -> next == OrderStatus.SHIPPED;
            case SHIPPED -> next == OrderStatus.DELIVERED;
            default -> false;
        };
    }
}
