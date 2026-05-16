package com.fizoind.stockflow_api.customer.service;

import com.fizoind.stockflow_api.customer.dto.CustomerRequestDTO;
import com.fizoind.stockflow_api.customer.dto.CustomerResponseDTO;
import com.fizoind.stockflow_api.customer.entity.Customer;
import com.fizoind.stockflow_api.customer.exception.CustomerNotFoundException;
import com.fizoind.stockflow_api.customer.mapper.CustomerMapper;
import com.fizoind.stockflow_api.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        logger.info("Starting to create customer.....");
        Customer customer = CustomerMapper.toEntity(customerRequestDTO);
        Customer saved_customer = customerRepository.save(customer);
        logger.info("Customer created successfully with its id {}", saved_customer.getId());
        return CustomerMapper.toDto(saved_customer);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        logger.info("Starting to fetch all customers......");
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public CustomerResponseDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        return CustomerMapper.toDto(customer);
    }

    public CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        CustomerMapper.updateEntity(customer, customerRequestDTO);
        Customer updated_customer = customerRepository.save(customer);
        return CustomerMapper.toDto(updated_customer);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepository.deleteById(customerId);
    }
}
