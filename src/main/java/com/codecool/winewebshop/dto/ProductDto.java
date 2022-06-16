package com.codecool.winewebshop.dto;


import com.codecool.winewebshop.entity.Category;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDto {

    private Long id;
    private String productName;
    private int vintage;
    private String productDescription;
    private int price;
    private int quantityInStock;
    private CategoryDto category;

}
