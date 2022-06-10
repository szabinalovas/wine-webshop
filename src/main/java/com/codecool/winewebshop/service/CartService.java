package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.dto.CartMapper;
import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public CartDto addCart(CartDto cartDto) {
        Cart cart = cartMapper.toEntity(cartDto);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public List<CartDto> findAllCart() {
        return cartMapper.toDto(cartRepository.findAll());
    }

    public CartDto findCartById(Long id) {
        return cartMapper.toDto(cartRepository.findById(id).get());
    }

    public CartDto updateCart(Long id, CartDto cartDto) {
        Cart cart = cartRepository.findById(id).get();
        cartMapper.updateCartFromDto(cartDto, cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }
}
