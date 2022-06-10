package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.Customer;
import com.codecool.winewebshop.entity.Payment;
import com.codecool.winewebshop.entity.Product;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CartDto {

    private Long id;
    private int total;
    private LocalDateTime date;
    private Customer customer;
    private List<Product> products;
    private Payment payment;
}
