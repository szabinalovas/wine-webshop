package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.service.CartService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cart")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Wine webshop",
        description = "Manage a webshop that sells hungarian wines.",
        version = "v1.0"))
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{customer_id}/{product_id}")
    @Operation(summary = "Add a product to a customers cart")
    public ResponseEntity<CartDto> addToCart(@PathVariable("customer_id") Long customerId,
                                             @PathVariable("product_id") Long productId) {
        CartDto cartDto;
        try {
            cartDto = cartService.addToCart(customerId, productId);
        } catch (NoSuchElementException e) {
            log.error("Customer or product not found");
            return ResponseEntity.notFound().build();
        }
        log.info("Successfully added to cart");
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/{customer_id}")
    @Operation(summary = "Get cart by customer id")
    public ResponseEntity<CartDto> findCartByCustomerId(@PathVariable("customer_id") Long customerId) {
        CartDto cart;
        try {
            cart = cartService.findDtoByCustomer(customerId);
        } catch (NoSuchElementException e) {
            log.error("Customer with id: " + customerId + " has no cart.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{customer_id}/{product_id}")
    @Operation(summary = "Delete product from customers cart")
    public ResponseEntity<Void> deleteProductFromCustomerCart(@PathVariable("customer_id") Long customerId,
                                                              @PathVariable("product_id") Long productId) {
        cartService.deleteProductFromCustomerCart(customerId, productId);
        log.info("Product with id: " + productId + " was deleted from customers cart.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customer_id}")
    @Operation(summary = "Delete customers cart")
    public ResponseEntity<Void> deleteByCustomer(@PathVariable("customer_id") Long customerId) {
        try {
            cartService.deleteByCustomer(customerId);
        } catch (EmptyResultDataAccessException e) {
            log.error("Customer cart with id: " + customerId + " not found.");
            return ResponseEntity.notFound().build();
        }
        log.info("Customers cart was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
