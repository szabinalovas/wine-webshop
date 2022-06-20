package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{customer_id}/{product_id}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("customer_id") Long customerId,
                                             @PathVariable("product_id") Long productId) {
        CartDto cartDto;
        try {
            cartDto = cartService.addToCart(customerId, productId);
        } catch (NoSuchElementException e) {
            log.error("Customer or product not found.");
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<CartDto> findCartByCustomerId(@PathVariable("customer_id") Long customerId) {
        CartDto cart;
        try {
            cart = cartService.findDtoByCustomer(customerId);
        } catch (NoSuchElementException e) {
            log.error("Customer with id: " + customerId + " not found.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{customer_id}/{product_id}")
    public ResponseEntity<Void> deleteProductFromCustomerCart(@PathVariable("customer_id") Long customerId,
                                                              @PathVariable("product_id") Long productId) {
        cartService.deleteProductFromCustomerCart(customerId, productId);
        log.info("Product with id: " + productId + " was deleted from customer' cart.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<Void> deleteByCustomer(@PathVariable("customer_id") Long customerId) {
        cartService.deleteByCustomer(customerId);
        log.info("Customers cart was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
