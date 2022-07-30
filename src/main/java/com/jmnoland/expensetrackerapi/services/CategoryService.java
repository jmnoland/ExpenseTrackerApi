package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.CategoryServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.CategoryMapper;
import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Category;
import com.jmnoland.expensetrackerapi.validators.category.CreateCategoryValidator;
import com.jmnoland.expensetrackerapi.validators.category.UpdateCategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements CategoryServiceInterface {

    private final CategoryRepositoryInterface categoryRepository;
    private final CategoryMapper mapper;
    @Autowired
    public CategoryService(CategoryRepositoryInterface categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = categoryMapper;
    }

    public List<CategoryDto> getAllCategories(String userId) {
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByUserId(userId);
        return this.mapper.entityToDto(categoryList);
    }

    public ServiceResponse<CategoryDto> insert(CategoryDto category) {
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByUserId(category.userId);

        List<ValidationError> validationErrors = new CreateCategoryValidator().validate(category, categoryList);

        Category newCategory = null;
        if (validationErrors.isEmpty()) {
            newCategory = this.mapper.dtoToEntity(category);
            try {
                this.categoryRepository.insert(newCategory);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newCategory),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public void delete(CategoryDto category) {
        Category existing = this.mapper.dtoToEntity(category);
        this.categoryRepository.delete(existing);
    }

    public ServiceResponse<CategoryDto> update(CategoryDto category) {
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByUserId(category.userId);

        List<ValidationError> validationErrors = new UpdateCategoryValidator().validate(category, categoryList);

        Category newCategory = null;
        if (validationErrors.isEmpty()) {
            newCategory = this.mapper.dtoToEntity(category);
            try {
                this.categoryRepository.update(newCategory);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newCategory),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}