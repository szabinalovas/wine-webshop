package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String customerUrl = "/customers";

    private final CustomerDto customer1 = new CustomerDto();
    private final CustomerDto customer2 = new CustomerDto();

    @BeforeEach
    public void init() {
        customer1.setId(1L);
        customer1.setCustomerName("Dianna Brickner");
        customer1.setCountry("Croatia");
        customer1.setPostalCode("32235");
        customer1.setCity("Bapska");
        customer1.setAddress("7595 Golf View Parkway");
        customer1.setEmail("dbrickner0@vk.com");
        customer1.setPhone("(414) 341-6616");

        customer2.setId(2L);
        customer2.setCustomerName("Margalo Dunstan");
        customer2.setCountry("China");
        customer2.setPostalCode("82334");
        customer2.setCity("Yanmen");
        customer2.setAddress("52 Fuller Point");
        customer2.setEmail("mdunstan4@surveymonkey.com");
        customer2.setPhone("(850) 712-8626");

        postCustomer(customer1);
        postCustomer(customer2);
    }

    @Test
    public void testGetAllCustomer() {
        ResponseEntity<CustomerDto[]> responseEntity = restTemplate.getForEntity(customerUrl, CustomerDto[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void testGetCustomerById() {
        ResponseEntity<CustomerDto> responseEntity = restTemplate.getForEntity(customerUrl + "/2", CustomerDto.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer2.getCustomerName(), responseEntity.getBody().getCustomerName());
        assertEquals(customer2.getCountry(), responseEntity.getBody().getCountry());
        assertEquals(customer2.getCity(), responseEntity.getBody().getCity());
        assertEquals(customer2.getEmail(), responseEntity.getBody().getEmail());

        ResponseEntity<CustomerDto> badResponse = restTemplate.getForEntity(customerUrl + "/5", CustomerDto.class);
        assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() {
        CustomerDto modifier = customer1;
        modifier.setCustomerName("Brianna Bricker");
        HttpEntity<CustomerDto> requestUpdate = createHttpEntity(modifier);
        restTemplate.exchange(customerUrl + "/1", HttpMethod.PUT, requestUpdate, Void.class);
        ResponseEntity<CustomerDto> modified = restTemplate.getForEntity(customerUrl + "/1", CustomerDto.class);
        assertEquals(modifier.getCustomerName(), modified.getBody().getCustomerName());
        assertEquals(modifier.getCountry(), modified.getBody().getCountry());
        assertEquals(modifier.getPostalCode(), modified.getBody().getPostalCode());
        assertEquals(modifier.getCity(), modified.getBody().getCity());
        assertEquals(modifier.getAddress(), modified.getBody().getAddress());
        assertEquals(modifier.getEmail(), modified.getBody().getEmail());
        assertEquals(modifier.getPhone(), modified.getBody().getPhone());

        modifier.setPhone("687sdf687");
        ResponseEntity<HttpStatus> updateEntity = restTemplate.exchange(customerUrl + "/1", HttpMethod.PUT,
                requestUpdate, HttpStatus.class);
        assertEquals(HttpStatus.BAD_REQUEST, updateEntity.getStatusCode());

    }

    @Test
    public void testUpdateWithWrongId() {
        CustomerDto modifier = customer1;
        modifier.setCustomerName("Brianna Bricker");
        HttpEntity<CustomerDto> requestUpdate = createHttpEntity(modifier);
        ResponseEntity<HttpStatus> updateEntity = restTemplate.exchange(customerUrl + "/6", HttpMethod.PUT,
                requestUpdate, HttpStatus.class);
        assertEquals(HttpStatus.NOT_FOUND, updateEntity.getStatusCode());
    }

    @Test
    public void postInvalidCustomer() {
        CustomerDto invalid = customer1;
        invalid.setCustomerName("Brianna 43526");
        HttpEntity<CustomerDto> httpEntity = createHttpEntity(invalid);
        ResponseEntity<CustomerDto> responseEntity = restTemplate.postForEntity(customerUrl, httpEntity, CustomerDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() {
        assertTrue(databaseContainsCustomerWithId(2L));
        restTemplate.delete(customerUrl + "/2");
        assertFalse(databaseContainsCustomerWithId(2L));
    }


    private void postCustomer(CustomerDto customerDto) {
        final HttpEntity<CustomerDto> httpEntity = createHttpEntity(customerDto);
        ResponseEntity<CustomerDto> postEntity = restTemplate.postForEntity(customerUrl, httpEntity, CustomerDto.class);
        assertEquals(HttpStatus.CREATED, postEntity.getStatusCode());
    }

    private HttpEntity<CustomerDto> createHttpEntity(CustomerDto customerDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(customerDto, headers);
    }

    private Boolean databaseContainsCustomerWithId(Long id) {
        CustomerDto[] customers = restTemplate.getForEntity(customerUrl, CustomerDto[].class).getBody();
        long count = Arrays.stream(customers).filter(c -> c.getId().equals(id)).count();
        return count > 0;
    }
}
