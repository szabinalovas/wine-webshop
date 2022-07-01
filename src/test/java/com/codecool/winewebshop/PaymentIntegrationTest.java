package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.*;
import com.codecool.winewebshop.entity.paymentEnums.PaymentStatus;
import com.codecool.winewebshop.entity.paymentEnums.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String paymentUrl = "/payments";
    private final PaymentDto payment = new PaymentDto();
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
        cart1.setId(1L);
        postToCart(cart1);

        payment.setId(1L);
        payment.setPaymentType(PaymentType.CARD_PAYMENT);
        postPayment(payment);
    }

    @Test
    public void testGetCartByPayment() {
        ResponseEntity<PaymentDto> responseEntity = restTemplate.getForEntity(paymentUrl + "/status/1", PaymentDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdatePayment() {
        PaymentDto modifier = payment;
        modifier.setPaymentStatus(PaymentStatus.COMPLETED);
        HttpEntity<PaymentDto> requestUpdate = createPaymentHttpEntity(modifier);
        restTemplate.exchange(paymentUrl + "/1", HttpMethod.PUT, requestUpdate, Void.class);
        ResponseEntity<PaymentDto> modified = restTemplate.getForEntity(paymentUrl + "/status/1", PaymentDto.class);
        assertEquals(modifier.getPaymentStatus(), modified.getBody().getPaymentStatus());

        modifier.setPaymentType(null);
        ResponseEntity<HttpStatus> updateEntity = restTemplate.exchange(paymentUrl + "/1", HttpMethod.PUT,
                requestUpdate, HttpStatus.class);
        assertEquals(HttpStatus.BAD_REQUEST, updateEntity.getStatusCode());
    }


    private void postPayment(PaymentDto paymentDto) {
        final HttpEntity<PaymentDto> httpEntity = createPaymentHttpEntity(paymentDto);
        ResponseEntity<PaymentDto> responseEntity = restTemplate.postForEntity(paymentUrl + "/1", httpEntity, PaymentDto.class);
    }

    private HttpEntity<PaymentDto> createPaymentHttpEntity(PaymentDto paymentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(paymentDto, headers);
    }

    private void postToCart(CartDto cartDto) {
        final HttpEntity<CartDto> httpEntity = createCartHttpEntity(cartDto);
        ResponseEntity<CartDto> responseEntity = restTemplate.postForEntity("/cart/" + customer1.getId() +
                "/" + product1.getId(), httpEntity, CartDto.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        responseEntity = restTemplate.postForEntity("/cart/4/8", httpEntity, CartDto.class);
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
