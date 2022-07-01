package com.codecool.winewebshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Email
    private String email;
    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", message = "Invalid phone number format!")
    private String phone;
    @OneToOne(mappedBy = "customer")
    private Cart cart;

}
