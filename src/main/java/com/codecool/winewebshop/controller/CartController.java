package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartDto addCart(@RequestBody CartDto cartDto) {
        return cartService.addCart(cartDto);
    }

    @GetMapping
    public List<CartDto> findAllCart() {
        return cartService.findAllCart();
    }

    @GetMapping("/{id}")
    public CartDto findCartById(@PathVariable("id") Long id) {
        return cartService.findCartById(id);
    }

    @PutMapping("/{id}")
    public CartDto updateCart(@PathVariable("id") Long id, @RequestBody CartDto cartDto) {
        return cartService.updateCart(id, cartDto);
    }

    @DeleteMapping("/{id}")
    public void deleteCartById(@PathVariable("id") Long id) {
        cartService.deleteCartById(id);
    }
}
