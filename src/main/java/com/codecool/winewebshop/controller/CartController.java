package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{customer_id}/{product_id}")
    public CartDto addToCart(@PathVariable("customer_id") Long customerId,
                             @PathVariable("product_id") Long productId) {
        return cartService.addToCart(customerId, productId);
    }

    @GetMapping("/{customer_id}")
    public CartDto findCartByCustomerId(@PathVariable("customer_id") Long customerId) {
        return cartService.findDtoByCustomer(customerId);
    }

    @DeleteMapping("/{customer_id}/{product_id}")
    public void deleteProductFromCustomerCart(@PathVariable("customer_id") Long customerId,
                                              @PathVariable("product_id") Long productId) {
        cartService.deleteProductFromCustomerCart(customerId, productId);
    }

    @DeleteMapping("/{customer_id}")
    public void deleteByCustomer(@PathVariable("customer_id") Long customerId) {
        cartService.deleteByCustomer(customerId);
    }
}
