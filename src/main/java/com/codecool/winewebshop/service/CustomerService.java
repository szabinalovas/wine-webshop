package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.dto.CustomerMapper;
import com.codecool.winewebshop.entity.Customer;
import com.codecool.winewebshop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> findAllCustomer() {
        return customerMapper.toDto(customerRepository.findAll());
    }

    public CustomerDto findCustomerDtoById(Long id) {
        return customerMapper.toDto(customerRepository.findById(id).orElse(null));
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);

        return customerMapper.toDto(customerRepository.save(customer));
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            return null;
        }
        customerMapper.updateCustomerFromDto(customerDto, customer);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
