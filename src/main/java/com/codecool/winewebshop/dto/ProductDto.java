package com.codecool.winewebshop.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Range(max = 2022)
    private Integer vintage;
    @Size(min = 10, max = 100, message = "Description should be more than 10 character.")
    private String productDescription;
    @Range(max = 50000)
    private Integer price;
    @NotNull
    private Integer quantityInStock;
    @NotNull
    private CategoryDto category;

}
