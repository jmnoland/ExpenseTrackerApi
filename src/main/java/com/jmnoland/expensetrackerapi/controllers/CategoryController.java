package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.helpers.RequestHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.CategoryServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final HttpServletRequest request;
    private final CategoryServiceInterface categoryService;

    @Autowired
    public CategoryController(CategoryServiceInterface categoryService, HttpServletRequest request) {
        this.categoryService = categoryService;
        this.request = request;
    }

    @GetMapping()
    public ServiceResponse<List<CategoryDto>> getCategories() {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.categoryService.getAllCategories(clientId);
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
