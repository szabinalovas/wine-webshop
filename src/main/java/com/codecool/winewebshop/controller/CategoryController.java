package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoryDto> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable("id") Long id) {
        CategoryDto category;
        try {
            category = categoryService.findCategoryById(id);
        } catch (NoSuchElementException e) {
            log.error("Category with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id,
                                                      @Valid @RequestBody CategoryDto categoryDto,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }

        CategoryDto category;
        try {
            category = categoryService.updateCategory(id, categoryDto);
        } catch (NoSuchElementException e) {
            log.error("Category with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        log.info("Category with id: " + id + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
