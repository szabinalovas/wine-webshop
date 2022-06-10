package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.dto.CategoryMapper;
import com.codecool.winewebshop.entity.Category;
import com.codecool.winewebshop.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public List<CategoryDto> findAllCategory() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    public CategoryDto findCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).get());
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).get();
        categoryMapper.updateCategoryFromDto(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
