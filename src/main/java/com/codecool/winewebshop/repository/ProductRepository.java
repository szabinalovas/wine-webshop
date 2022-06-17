package com.codecool.winewebshop.repository;

import com.codecool.winewebshop.entity.Category;
import com.codecool.winewebshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
