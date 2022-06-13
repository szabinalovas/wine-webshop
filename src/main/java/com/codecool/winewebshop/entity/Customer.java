package com.codecool.winewebshop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "customerName")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String country;
    private String postalCode;
    private String city;
    private String address;
    private String phone;
    private String email;
    private String creditCardNumber;
    private String creditCardExpiryDate;
    @OneToOne(mappedBy = "customer")
    private Cart cart;

}
