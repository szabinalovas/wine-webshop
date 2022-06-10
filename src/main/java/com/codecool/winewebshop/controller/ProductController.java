package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }
}

