package com.codecool.winewebshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotEmpty
    private String productName;
    private Integer vintage;
    @Size(min = 10, max = 100, message = "Description should be more than 10 character.")
    private String productDescription;
    private Integer price;
    private Integer quantityInStock;
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
    private List<Cart> carts;
}
