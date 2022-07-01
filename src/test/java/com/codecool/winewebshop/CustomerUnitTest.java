package com.codecool.winewebshop;

import com.codecool.winewebshop.controller.CustomerController;
import com.codecool.winewebshop.dto.CustomerDto;
import com.codecool.winewebshop.service.CustomerService;
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
public class CustomerUnitTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

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
        customer1.setPhone("(414) 3416616");
        customer1.setEmail("dbrickner0@vk.com");

        customer2.setId(2L);
        customer2.setCustomerName("Margalo Dunstan");
        customer2.setCountry("China");
        customer2.setPostalCode("82334");
        customer2.setCity("Yanmen");
        customer2.setAddress("52 Fuller Point");
        customer2.setPhone("(850) 7128626");
        customer2.setEmail("mdunstan4@surveymonkey.com");
    }

    @Test
    public void findAll_shouldReturnAllCustomer() {
        when(customerService.findAllCustomer()).thenReturn(List.of(customer1, customer2));
        List<CustomerDto> result = customerController.findAllCustomer();
        assertEquals(List.of(customer1, customer2), result);
        assertEquals(customer1, result.get(0));
    }

    @Test
    public void findById_shouldReturnOneCustomer() {
        when(customerService.findCustomerDtoById(1L)).thenReturn(customer1);
        ResponseEntity<CustomerDto> responseEntity = customerController.findCustomerById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer1, responseEntity.getBody());
    }

    @Test
    public void findOneByWrongId_shouldRespond404() {
        when(customerService.findCustomerDtoById(3L)).thenThrow(NoSuchElementException.class);
        ResponseEntity<CustomerDto> responseEntity = customerController.findCustomerById(3L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void deleteCustomer() {
        Mockito.doNothing().when(customerService).deleteCustomerById(1L);
        ResponseEntity<Void> responseEntity = customerController.deleteCustomerById(1L);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
