package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.interfaces.services.CategoryServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryServiceInterface categoryService;

    @Autowired
    public CategoryController(CategoryServiceInterface categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<CategoryDto> getCategories(String userId) {
        return this.categoryService.getAllCategories(userId);
    }

    @PostMapping()
    public ServiceResponse<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return this.categoryService.insert(categoryDto);
    }

    @PatchMapping()
    public ServiceResponse<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        return this.categoryService.update(categoryDto);
    }

    @DeleteMapping()
    public void deleteCategory(@RequestBody CategoryDto categoryDto) {
        this.categoryService.delete(categoryDto);
    }
}
