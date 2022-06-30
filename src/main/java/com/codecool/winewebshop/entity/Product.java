package com.codecool.winewebshop.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
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
    private String productDescription;
    private Integer price;
    private Integer quantityInStock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
}
