package com.fizoind.stockflow_api.orderItem.entity;

import com.fizoind.stockflow_api.auditing.Auditable;
import com.fizoind.stockflow_api.order.entity.CustomerOrder;
import com.fizoind.stockflow_api.product.entity.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal priceAtOrderTime;

    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderItem() {

    }

    public OrderItem(Long id, Integer quantity, BigDecimal priceAtOrderTime, BigDecimal subTotal, CustomerOrder order, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.priceAtOrderTime = priceAtOrderTime;
        this.subTotal = subTotal;
        this.order = order;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(BigDecimal priceAtOrderTime) {
        this.priceAtOrderTime = priceAtOrderTime;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
