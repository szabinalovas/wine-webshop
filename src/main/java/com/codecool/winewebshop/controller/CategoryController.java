package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.CategoryDto;
import com.codecool.winewebshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Operation(summary = "Add new wine category")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }
        log.info("Category added");
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all category")
    public List<CategoryDto> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
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
    @Operation(summary = "Update an existing category")
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
        log.info("Category updated");
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing category")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategoryById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Category with id: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        log.info("Category with id: " + id + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
