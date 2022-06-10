package com.codecool.winewebshop.dto;


import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Category;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDto {

    private Long id;
    private String productName;
    private String productDescription;
    private int price;
    private int quantityInStock;
    private Category category;
    private List<Cart> carts;
}
