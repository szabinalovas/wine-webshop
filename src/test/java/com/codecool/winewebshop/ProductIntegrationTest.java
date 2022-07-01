package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String productUrl = "/products";

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
    }

    @Test
    public void postInvalidProduct() {
        product1.setProductName("");
        final HttpEntity<ProductDto> httpEntity = createHttpEntity(product1);
        ResponseEntity<ProductDto> postEntity = restTemplate.postForEntity(productUrl, httpEntity, ProductDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, postEntity.getStatusCode());
    }

    @Test
    public void testGetAllProduct() {
        ResponseEntity<ProductDto[]> responseEntity = restTemplate.getForEntity(productUrl, ProductDto[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void testGetProductById() {
        ResponseEntity<ProductDto> responseEntity = restTemplate.getForEntity(productUrl + "/2", ProductDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product2.getProductName(), responseEntity.getBody().getProductName());
        assertEquals(product2.getVintage(), responseEntity.getBody().getVintage());
        assertEquals(product2.getProductDescription(), responseEntity.getBody().getProductDescription());
        assertEquals(product2.getPrice(), responseEntity.getBody().getPrice());

        ResponseEntity<ProductDto> badResponse = restTemplate.getForEntity(productUrl + "/5", ProductDto.class);
        assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
    }

    @Test
    public void testGetProductsByCategoryId() {
        ResponseEntity<ProductDto[]> responseEntity = restTemplate.getForEntity(productUrl + "/category/"
                + category2.getId(), ProductDto[].class);
        ProductDto[] products = responseEntity.getBody();
        CategoryDto categoryDto = Arrays.stream(products).map(ProductDto::getCategory).findFirst().orElseThrow();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category2.getCategoryType(), categoryDto.getCategoryType());

        responseEntity = restTemplate.getForEntity(productUrl + "/category/5", ProductDto[].class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() {
        ProductDto modifier = product1;
        modifier.setPrice(3500);
        HttpEntity<ProductDto> requestUpdate = createHttpEntity(modifier);
        restTemplate.exchange(productUrl + "/1", HttpMethod.PUT, requestUpdate, Void.class);
        ResponseEntity<ProductDto> modified = restTemplate.getForEntity(productUrl + "/1", ProductDto.class);
        assertEquals(modifier.getProductName(), modified.getBody().getProductName());
        assertEquals(modifier.getPrice(), modified.getBody().getPrice());
        assertEquals(modifier.getProductDescription(), modified.getBody().getProductDescription());

        modifier.setVintage(2030);
        ResponseEntity<HttpStatus> updateEntity = restTemplate.exchange(productUrl + "/1", HttpMethod.PUT,
                requestUpdate, HttpStatus.class);
        assertEquals(HttpStatus.BAD_REQUEST, updateEntity.getStatusCode());

    }

    @Test
    public void testUpdateWithWrongId() {
        ProductDto modifier = product1;
        modifier.setProductName("Bunny");
        HttpEntity<ProductDto> requestUpdate = createHttpEntity(modifier);
        ResponseEntity<HttpStatus> updateEntity = restTemplate.exchange(productUrl + "/6", HttpMethod.PUT,
                requestUpdate, HttpStatus.class);
        assertEquals(HttpStatus.NOT_FOUND, updateEntity.getStatusCode());
    }

    @Test
    public void testDeleteProduct() {
        assertTrue(databaseContainsProductWithId(2L));
        restTemplate.delete(productUrl + "/2");
        assertFalse(databaseContainsProductWithId(2L));
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
        ResponseEntity<ProductDto> postEntity = restTemplate.postForEntity(productUrl, httpEntity, ProductDto.class);
        assertEquals(HttpStatus.CREATED, postEntity.getStatusCode());
    }

    private HttpEntity<ProductDto> createHttpEntity(ProductDto productDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(productDto, headers);
    }

    private Boolean databaseContainsProductWithId(Long id) {
        ProductDto[] products = restTemplate.getForEntity(productUrl, ProductDto[].class).getBody();
        long count = Arrays.stream(products).filter(c -> c.getId().equals(id)).count();
        return count > 0;
    }
}
