package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.dto.ProductMapper;
import com.codecool.winewebshop.entity.Category;
import com.codecool.winewebshop.entity.Product;
import com.codecool.winewebshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    public List<ProductDto> findAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    public ProductDto findProductById(Long id) {
        return productMapper.toDto(productRepository.findById(id).orElseThrow());
    }

    public List<ProductDto> getProductByCategoryId(Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return productMapper.toDto(productRepository.findByCategory(category));
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow();
        productMapper.updateProductFromDto(productDto, product);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void decreaseStock(Product product) {
        product.setQuantityInStock(product.getQuantityInStock() - 1);
    }
}
