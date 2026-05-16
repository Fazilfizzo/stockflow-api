package com.fizoind.stockflow_api.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SupplierCreateDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "phone must not be blank")
    private String phone;

    @NotBlank(message = "email must not be blank")
    @Pattern(regexp = "^[\\w.+-]+@gmail\\.com$", message = "Email must be valid gmail address")
    private String email;


    @NotBlank(message = "address must not be blank")
    @Pattern(regexp = "^P\\.O BOX.+$", message = "Address must start with 'P.O BOX' and have something after it")
    private String address;

    public SupplierCreateDTO() {

    }

    public SupplierCreateDTO(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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
}

