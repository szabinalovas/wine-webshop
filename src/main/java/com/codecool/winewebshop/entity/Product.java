package com.codecool.winewebshop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productName")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int vintage;
    private String productDescription;
    private int price;
    private int quantityInStock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "product_cart",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"),
            foreignKey = @ForeignKey(name = "fk_cart_product"),
            inverseForeignKey = @ForeignKey(name = "fk_product_cart"))
    @JsonIdentityReference(alwaysAsId = true)
    private List<Cart> carts;


}
