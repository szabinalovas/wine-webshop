package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{customer_id}/{product_id}")
    public ResponseEntity<CartDto> addToCart(@PathVariable("customer_id") Long customerId,
                                             @PathVariable("product_id") Long productId) {

        return new ResponseEntity<>(cartService.addToCart(customerId, productId), HttpStatus.CREATED);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<CartDto> findCartByCustomerId(@PathVariable("customer_id") Long customerId) {
        CartDto cart = cartService.findDtoByCustomer(customerId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{customer_id}/{product_id}")
    public ResponseEntity<Void> deleteProductFromCustomerCart(@PathVariable("customer_id") Long customerId,
                                                              @PathVariable("product_id") Long productId) {
        cartService.deleteProductFromCustomerCart(customerId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<Void> deleteByCustomer(@PathVariable("customer_id") Long customerId) {
        cartService.deleteByCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
