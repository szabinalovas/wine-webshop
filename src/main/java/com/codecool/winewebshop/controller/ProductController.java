package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all product")
    public List<ProductDto> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long id) {
        ProductDto product;
        try {
            product = productService.findProductById(id);
        } catch (NoSuchElementException e) {
            log.error("Product with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category_id}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable("category_id") Long categoryId) {
        List<ProductDto> products;
        try {
            products = productService.getProductByCategoryId(categoryId);
        } catch (NoSuchElementException e) {
            log.error("Category with id: " + categoryId + " not found.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Add new product")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Product added");
        return ResponseEntity.ok(productService.addProduct(productDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,
                                                    @Valid @RequestBody ProductDto productDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }

        ProductDto product;
        try {
            product = productService.updateProduct(id, productDto);
        } catch (NoSuchElementException e) {
            log.error("Product with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        log.info("Product updated");
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Product with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        log.info("Product with id: " + id + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

