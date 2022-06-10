package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.dto.ProductMapper;
import com.codecool.winewebshop.entity.Product;
import com.codecool.winewebshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public List<ProductDto> findAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    public ProductDto findProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id).get());
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).get();
        productMapper.updateProductFromDto(productDto, product);
        return productMapper.toDto(productRepository.save(product));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
