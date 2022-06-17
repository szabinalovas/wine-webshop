package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> findAllCustomer() {
        return customerService.findAllCustomer();
    }

    @GetMapping("/{id}")
    public CustomerDto findCustomerById(@PathVariable("id") Long id) {
        return customerService.findCustomerDtoById(id);
    }

    @PostMapping
    public CustomerDto addCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.addCustomer(customerDto);
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
