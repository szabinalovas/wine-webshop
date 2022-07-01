package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get all customer")
    public List<CustomerDto> findAllCustomer() {
        return customerService.findAllCustomer();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("id") Long id) {
        CustomerDto customerDto;
        try {
            customerDto = customerService.findCustomerDtoById(id);
        } catch (NoSuchElementException e) {
            log.error("Customer with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerDto);
    }


    @PostMapping
    @Operation(summary = "Add new customer")
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        log.info("Customer added");
        return new ResponseEntity<>(customerService.addCustomer(customerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer by id")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
                                                      @Valid @RequestBody CustomerDto customerDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }

        CustomerDto customer;

        try {
            customer = customerService.updateCustomer(id, customerDto);
        } catch (NoSuchElementException e) {
            log.error("Customer with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        log.info("Customer updated");
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer by id")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") Long id) {
        try {
            customerService.deleteCustomerById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Customer with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        log.info("Customer with id: " + id + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
