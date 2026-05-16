package com.fizoind.stockflow_api.customer.mapper;

import com.fizoind.stockflow_api.customer.dto.CustomerRequestDTO;
import com.fizoind.stockflow_api.customer.dto.CustomerResponseDTO;
import com.fizoind.stockflow_api.customer.entity.Customer;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequestDTO customerRequestDTO) {
        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setPhone(customerRequestDTO.getPhone());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setAddress(customerRequestDTO.getAddress());
        return customer;
    }

    public static CustomerResponseDTO toDto(Customer customer) {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setName(customer.getName());
        customerResponseDTO.setPhone(customer.getPhone());
        customerResponseDTO.setEmail(customer.getEmail());
        customerResponseDTO.setAddress(customer.getAddress());
        return customerResponseDTO;
    }

    public static void updateEntity(Customer customer, CustomerRequestDTO customerRequestDTO) {
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setPhone(customerRequestDTO.getPhone());
        customer.setAddress(customerRequestDTO.getAddress());
    }
}
