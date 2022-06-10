package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.Cart;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomerDto {

    private Long id;
    private String customerName;
    private String country;
    private String postalCode;
    private String city;
    private String address;
    private String telephoneNumber;
    private String email;
    private String creditCardNumber;
    private String creditCardHolder;
    private String creditCardExpiryDate;
    private Cart cart;
}
