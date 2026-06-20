package com.fizoind.stockflow_api.supplier.dto;

import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;

public class SupplierResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private SupplierStatus status;

    public SupplierResponseDTO(Long id, String name, String email, String phone, String address, SupplierStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public SupplierResponseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

