package com.codecool.winewebshop;

import com.codecool.winewebshop.controller.CategoryController;
import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryUnitTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private final CategoryDto category1 = new CategoryDto();
    private final CategoryDto category2 = new CategoryDto();

    @BeforeEach
    public void init() {
        category1.setId(1L);
        category1.setCategoryType("red");

        category2.setId(2L);
        category2.setCategoryType("white");
    }

    @Test
    public void findAll_shouldReturnAllCategory() {
        when(categoryService.findAllCategory()).thenReturn(List.of(category1, category2));
        List<CategoryDto> result = categoryController.findAllCategory();
        assertEquals(List.of(category1, category2), result);
        assertEquals(category1, result.get(0));
    }

    @Test
    public void findById_shouldReturnOneCategory() {
        when(categoryService.findCategoryById(1L)).thenReturn(category1);
        ResponseEntity<CategoryDto> responseEntity = categoryController.findCategoryById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(category1);
    }

    @Test
    public void findOneByWrongId_shouldRespond404() {
        when(categoryService.findCategoryById(3L)).thenThrow(NoSuchElementException.class);
        ResponseEntity<CategoryDto> responseEntity = categoryController.findCategoryById(3L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
    }


    @Test
    public void deleteCategory() {
        Mockito.doNothing().when(categoryService).deleteCategoryById(1L);
        ResponseEntity<Void> responseEntity = categoryController.deleteCategoryById(1L);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

}
