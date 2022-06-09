package com.codecool.winewebshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(mappedBy = "customer")
    private Cart cart;

}
