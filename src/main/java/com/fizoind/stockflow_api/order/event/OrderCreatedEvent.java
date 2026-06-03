package com.fizoind.stockflow_api.order.event;

import com.fizoind.stockflow_api.order.entity.CustomerOrder;

public class OrderCreatedEvent {
   private CustomerOrder customerOrder;

    public OrderCreatedEvent(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }
}
