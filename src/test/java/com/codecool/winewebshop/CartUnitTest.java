package com.codecool.winewebshop;

import com.codecool.winewebshop.controller.CartController;
import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartUnitTest {
    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    private final CartDto cart1 = new CartDto();
    private final CartDto cart2 = new CartDto();
    private final CustomerDto customer1 = new CustomerDto();
    private final CustomerDto customer2 = new CustomerDto();
    private final ProductDto product1 = new ProductDto();
    private final ProductDto product2 = new ProductDto();
    private final CategoryDto category1 = new CategoryDto();
    private final CategoryDto category2 = new CategoryDto();


    @BeforeEach
    public void init() {
        customer1.setId(1L);
        customer1.setCustomerName("Dianna Brickner");
        customer1.setCountry("Croatia");
        customer1.setPostalCode("32235");
        customer1.setCity("Bapska");
        customer1.setAddress("7595 Golf View Parkway");
        customer1.setPhone("(414) 3416616");
        customer1.setEmail("dbrickner0@vk.com");

        customer2.setId(1L);
        customer2.setCustomerName("Margalo Dunstan");
        customer2.setCountry("China");
        customer2.setPostalCode("82334");
        customer2.setCity("Yanmen");
        customer2.setAddress("52 Fuller Point");
        customer2.setPhone("(850) 7128626");
        customer2.setEmail("mdunstan4@surveymonkey.com");

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

        cart1.setId(1L);
        cart1.setTotal(2150);
        cart1.setCustomer(customer1);
        cart1.setProducts(List.of(product1));

        cart2.setId(2L);
        cart2.setTotal(4350);
        cart2.setCustomer(customer2);
        cart2.setProducts(List.of(product1, product2));
    }

    @Test
    public void findByCustomerId() {
        when(cartService.findDtoByCustomer(1L)).thenReturn(cart1);
        ResponseEntity<CartDto> responseEntity = cartController.findCartByCustomerId(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(cart1);
        assertThat(responseEntity.getBody().getProducts()).isEqualTo(List.of(product1));
    }




}
