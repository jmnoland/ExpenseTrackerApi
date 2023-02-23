package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.List;

public interface CategoryServiceInterface {

    ServiceResponse<List<CategoryDto>> getAllCategories(String clientId);

    ServiceResponse<CategoryDto> createCategory(String name, String clientId);

    ServiceResponse<CategoryDto> updateCategory(String name, String clientId, String categoryId);

    ServiceResponse<CategoryDto> insert(CategoryDto category);

    void delete(CategoryDto category);

    ServiceResponse<CategoryDto> update(CategoryDto category);
}
