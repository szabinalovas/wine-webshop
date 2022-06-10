package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
    }

    @GetMapping
    public List<CustomerDto> findAllCustomer() {
        return customerService.findAllCustomer();
    }

    @GetMapping("/{id}")
    public CustomerDto findCustomerById(@PathVariable("id") Long id) {
        return customerService.findCustomerById(id);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }
}
