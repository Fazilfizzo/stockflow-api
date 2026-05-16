package com.fizoind.stockflow_api.supplier.entity;

import com.fizoind.stockflow_api.auditing.Auditable;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.stockmovement.entity.StockMovement;
import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Supplier extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    private String address;

    @Enumerated(EnumType.STRING)
    private SupplierStatus status;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "supplier")
    private List<StockMovement> stockMovements = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(Long id, String name, String phone, String email, String address, SupplierStatus status, List<Product> products, List<StockMovement> stockMovements) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        this.products = products;
        this.stockMovements = stockMovements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SupplierStatus getStatus() {
        return status;
    }

    public void setStatus(SupplierStatus status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", products='" + products + '\'' +
                ", stock_movements='" + stockMovements + '\'' +
                '}';
    }
}


