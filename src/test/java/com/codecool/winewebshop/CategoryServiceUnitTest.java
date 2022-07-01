package com.codecool.winewebshop;

import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.CategoryMapper;
import com.codecool.winewebshop.entity.Category;
import com.codecool.winewebshop.repository.CategoryRepository;
import com.codecool.winewebshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    private final CategoryDto categoryDto1 = new CategoryDto();
    private final CategoryDto categoryDto2 = new CategoryDto();

    private final Category category1 = new Category();
    private final Category category2 = new Category();

    @BeforeEach
    public void init() {
        categoryDto1.setId(1L);
        categoryDto1.setCategoryType("red");

        categoryDto2.setId(2L);
        categoryDto2.setCategoryType("white");

        category1.setId(1L);
        category1.setCategoryType("red");
        category1.setProducts(null);

        category2.setId(2L);
        category2.setCategoryType("white");
        category2.setProducts(null);


    }

    @Test
    public void testAddCategory() {
        when(categoryMapper.toEntity(categoryDto1)).thenReturn(category1);
        when(categoryMapper.toDto(categoryRepository.save(category1))).thenReturn(categoryDto1);
        CategoryDto categoryDto = categoryService.addCategory(categoryDto1);
        assertEquals(categoryDto1, categoryDto);
    }

    @Test
    public void testFindAllCategory() {
        when(categoryMapper.toDto(categoryRepository.findAll())).thenReturn(List.of(categoryDto1, categoryDto2));
        List<CategoryDto> result = categoryService.findAllCategory();
        assertEquals(List.of(categoryDto1, categoryDto2), result);
        assertEquals(categoryDto1, result.get(0));
    }

    @Test
    public void testFindCategoryById() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        when(categoryMapper.toDto(category2)).thenReturn(categoryDto2);
        CategoryDto result = categoryService.findCategoryById(2L);
        assertEquals(categoryDto2, result);
    }

    @Test
    public void testUpdateCategory() {
        CategoryDto categoryDtoToUpdate = new CategoryDto(1L, "rose");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryMapper.toDto(categoryRepository.save(category1))).thenReturn(categoryDtoToUpdate);
        CategoryDto result = categoryService.updateCategory(1L, categoryDtoToUpdate);
        assertEquals(categoryDtoToUpdate.getCategoryType(), result.getCategoryType());
    }


}
