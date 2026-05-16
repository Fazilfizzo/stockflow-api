package com.fizoind.stockflow_api.supplier.dto;

import com.fizoind.stockflow_api.supplier.entity.SupplierStatus;

public class SupplierResponseDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private SupplierStatus status;

    public SupplierResponseDTO(String phone, String email, String name, String address, SupplierStatus status) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.address = address;
        this.status = status;
    }

    public SupplierResponseDTO() {

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

