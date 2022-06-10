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

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);

        return customerMapper.toDto(customerRepository.save(customer));
    }

    public List<CustomerDto> findAllCustomer() {
        return customerMapper.toDto(customerRepository.findAll());
    }

    public CustomerDto findCustomerById(Long id) {
        return customerMapper.toDto(customerRepository.findById(id).get());
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).get();
        customerMapper.updateCustomerFromDto(customerDto, customer);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
