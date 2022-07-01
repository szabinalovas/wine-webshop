package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String cartUrl = "/cart";

    private final CartDto cart1 = new CartDto();
    private final CustomerDto customer1 = new CustomerDto();
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
        customer1.setPhone("(414) 341-6616");
        customer1.setEmail("dbrickner0@vk.com");

        postCustomer(customer1);

        category1.setId(1L);
        category1.setCategoryType("white");

        category2.setId(2L);
        category2.setCategoryType("red");

        postCategory(category1);
        postCategory(category2);

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

        postProduct(product1);
        postProduct(product2);

        postToCart(cart1);
    }


    @Test
    public void testGetCartByCustomerId() {
        ResponseEntity<CartDto> responseEntity = restTemplate.getForEntity(cartUrl + "/" + customer1.getId(), CartDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer1.getCustomerName(), responseEntity.getBody().getCustomer().getCustomerName());
    }

    @Test
    public void testDeleteProductFromCustomerCart() {
        ResponseEntity<CartDto> responseEntity = restTemplate.getForEntity(cartUrl + "/1", CartDto.class);
        List<ProductDto> productsBeforeDelete = responseEntity.getBody().getProducts();
        restTemplate.delete(cartUrl + "/1/1");
        responseEntity = restTemplate.getForEntity(cartUrl + "/1", CartDto.class);
        List<ProductDto> productsAfterDelete = responseEntity.getBody().getProducts();
        assertEquals(productsBeforeDelete.size() - 1, productsAfterDelete.size());
    }

    @Test
    public void testDeleteCustomersCart() {
        ResponseEntity<CartDto> responseEntity = restTemplate.getForEntity(cartUrl + "/1", CartDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        restTemplate.delete(cartUrl + "/1");

        responseEntity = restTemplate.getForEntity(cartUrl + "/8", CartDto.class);
        restTemplate.delete(cartUrl + "/8");
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private void postToCart(CartDto cartDto) {
        final HttpEntity<CartDto> httpEntity = createCartHttpEntity(cartDto);
        ResponseEntity<CartDto> responseEntity = restTemplate.postForEntity(cartUrl + "/" + customer1.getId() + "/" + product1.getId(), httpEntity, CartDto.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        responseEntity = restTemplate.postForEntity(cartUrl + "/4/8", httpEntity, CartDto.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private HttpEntity<CartDto> createCartHttpEntity(CartDto cartDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(cartDto, headers);
    }

    private void postCategory(CategoryDto categoryDto) {
        final HttpEntity<CategoryDto> httpEntity = createCategoryHttpEntity(categoryDto);
        ResponseEntity<CategoryDto> postEntity = restTemplate.postForEntity("/category", httpEntity, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, postEntity.getStatusCode());
    }

    private HttpEntity<CategoryDto> createCategoryHttpEntity(CategoryDto categoryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(categoryDto, headers);
    }

    private void postProduct(ProductDto productDto) {
        final HttpEntity<ProductDto> httpEntity = createHttpEntity(productDto);
        ResponseEntity<ProductDto> postEntity = restTemplate.postForEntity("/products", httpEntity, ProductDto.class);
        assertEquals(HttpStatus.CREATED, postEntity.getStatusCode());
    }

    private HttpEntity<ProductDto> createHttpEntity(ProductDto productDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(productDto, headers);
    }

    private void postCustomer(CustomerDto customerDto) {
        final HttpEntity<CustomerDto> httpEntity = createHttpEntity(customerDto);
        ResponseEntity<CustomerDto> postEntity = restTemplate.postForEntity("/customers", httpEntity, CustomerDto.class);
        assertEquals(HttpStatus.CREATED, postEntity.getStatusCode());
    }

    private HttpEntity<CustomerDto> createHttpEntity(CustomerDto customerDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(customerDto, headers);
    }
}
