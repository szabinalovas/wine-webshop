package com.codecool.winewebshop.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductDto {

    private Long id;
    @NotBlank
    @NotEmpty
    private String productName;
    private Integer vintage;
    @Size(min = 10, max = 100, message = "Description should be more than 10 character.")
    private String productDescription;
    private Integer price;
    private Integer quantityInStock;
    private CategoryDto category;

}
