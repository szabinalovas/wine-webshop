package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String categoryUrl = "/category";

    @Test
    public void testGetAllCategory() {
        ResponseEntity<CategoryDto[]> responseEntity = restTemplate.getForEntity(categoryUrl, CategoryDto[].class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().length);
    }

    @Test
    public void testGetCategoryById() {
        ResponseEntity<CategoryDto> response = restTemplate.getForEntity(categoryUrl + "/1", CategoryDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetCategoryByWrongId() {
        ResponseEntity<CategoryDto> badResponse = restTemplate.getForEntity(categoryUrl + "/9", CategoryDto.class);
        assertEquals(HttpStatus.NOT_FOUND, badResponse.getStatusCode());
    }

    @Test
    public void postInvalidCategory() {
        CategoryDto invalidCategory = new CategoryDto(3L, "");
        final HttpEntity<CategoryDto> httpEntity = createHttpEntity(invalidCategory);
        ResponseEntity<CategoryDto> postEntity = restTemplate.postForEntity(categoryUrl, httpEntity, CategoryDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, postEntity.getStatusCode());
    }

    @Test
    public void testUpdateCategory() {
        CategoryDto modifier = new CategoryDto();
        modifier.setCategoryType("dry");
        HttpEntity<CategoryDto> requestUpdate = createHttpEntity(modifier);
        restTemplate.exchange(categoryUrl + "/1", HttpMethod.PUT, requestUpdate, Void.class);
        ResponseEntity<CategoryDto> modified = restTemplate.getForEntity(categoryUrl + "/1", CategoryDto.class);
        assertEquals(modifier.getCategoryType(), modified.getBody().getCategoryType());
    }

    private HttpEntity<CategoryDto> createHttpEntity(CategoryDto categoryDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(categoryDto, headers);
    }

}
