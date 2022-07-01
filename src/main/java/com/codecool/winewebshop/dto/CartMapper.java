package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.Cart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart cart);

    Cart toEntity(CartDto cartDto);

    List<CartDto> toDto(List<Cart> carts);
}
