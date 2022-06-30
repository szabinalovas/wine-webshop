package com.codecool.winewebshop.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CategoryDto {

    private Long id;
    @NotBlank
    @NotEmpty
    private String categoryType;
}
