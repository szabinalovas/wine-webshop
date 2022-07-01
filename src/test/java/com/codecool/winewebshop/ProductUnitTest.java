package com.codecool.winewebshop;

import com.codecool.winewebshop.controller.ProductController;
import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductUnitTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

    private final ProductDto product1 = new ProductDto();
    private final ProductDto product2 = new ProductDto();
    private final CategoryDto category1 = new CategoryDto();
    private final CategoryDto category2 = new CategoryDto();

    @BeforeEach
    public void init() {
        category1.setId(1L);
        category1.setCategoryType("white");

        category2.setId(2L);
        category2.setCategoryType("red");

        product1.setId(1L);
        product1.setProductName("RUPPERT A Nyúl");
        product1.setVintage(2021);
        product1.setProductDescription("It’s tasty, rich and substantial, complemented with the lively acids that...");
        product1.setPrice(2150);
        product1.setQuantityInStock(28);
        product1.setCategory(category1);

        product2.setId(2L);
        product2.setProductName("RUPPERT Kandúr Cuvée");
        product2.setVintage(2020);
        product2.setProductDescription("The wine has a ripe character, a soft palate that...");
        product2.setPrice(2200);
        product2.setQuantityInStock(15);
        product2.setCategory(category2);

    }

    @Test
    public void findAll_shouldReturnAllProduct() {
        when(productService.findAllProducts()).thenReturn(List.of(product1, product2));
        List<ProductDto> result = productController.findAllProducts();
        assertEquals(List.of(product1, product2), result);
        assertEquals(product1, result.get(0));
    }

    @Test
    public void findById_shouldReturnOneProduct() {
        when(productService.findProductById(1L)).thenReturn(product1);
        ResponseEntity<ProductDto> responseEntity = productController.findProductById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product1, responseEntity.getBody());
    }

    @Test
    public void findOneByWrongId_shouldRespond404() {
        when(productService.findProductById(3L)).thenThrow(NoSuchElementException.class);
        ResponseEntity<ProductDto> responseEntity = productController.findProductById(3L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void deleteProduct() {
        Mockito.doNothing().when(productService).deleteProductById(1L);
        ResponseEntity<Void> responseEntity = productController.deleteProductById(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
