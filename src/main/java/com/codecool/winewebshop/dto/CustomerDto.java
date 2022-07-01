package com.codecool.winewebshop.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomerDto {

    private Long id;
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Name only contains letters and ' or . or - characters")
    private String customerName;
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Country name only contains letters and ' or . or - characters")
    private String country;
    @NotEmpty
    private String postalCode;
    @Pattern(regexp = "^\\p{Lu}\\p{L}*(?:[\\s-]\\p{Lu}\\p{L}*)*$", message = "City name only contains letters")
    private String city;
    @NotBlank
    private String address;
    @Email
    private String email;
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", message = "Phone number format must be: (###) ###-####")
    private String phone;

}
