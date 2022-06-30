package com.codecool.winewebshop.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

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
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Invalid name!")
    private String customerName;
    private String country;
    private String postalCode;
    @Pattern(regexp = "^\\p{Lu}\\p{L}*(?:[\\s-]\\p{Lu}\\p{L}*)*$", message = "Invalid city name")
    private String city;
    private String address;
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", message = "Invalid phone number format!")
    private String phone;
    @Email
    private String email;
}
