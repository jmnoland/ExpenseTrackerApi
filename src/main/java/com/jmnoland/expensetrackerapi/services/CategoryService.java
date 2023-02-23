package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
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

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService implements CategoryServiceInterface {

    private final CategoryRepositoryInterface categoryRepository;
    private final CategoryMapper mapper;
    private final DateProviderInterface dateProvider;
    @Autowired
    public CategoryService(CategoryRepositoryInterface categoryRepository,
                           CategoryMapper categoryMapper,
                           DateProviderInterface dateProvider) {
        this.categoryRepository = categoryRepository;
        this.mapper = categoryMapper;
        this.dateProvider = dateProvider;
    }

    public ServiceResponse<List<CategoryDto>> getAllCategories(String clientId) {
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByClientId(clientId);
        List<CategoryDto> list = this.mapper.entityToDto(categoryList);

        return new ServiceResponse<>(list, true, null);
    }

    public ServiceResponse<CategoryDto> createCategory(String name, String clientId) {
        Date dateTimeNow = dateProvider.getDateNow().getTime();
        CategoryDto categoryDto = new CategoryDto(UUID.randomUUID().toString(), clientId, name, dateTimeNow);
        return insert(categoryDto);
    }

    public ServiceResponse<CategoryDto> updateCategory(String name, String clientId, String categoryId) {
        Date dateTimeNow = dateProvider.getDateNow().getTime();
        CategoryDto categoryDto = new CategoryDto(categoryId, clientId, name, null);
        return update(categoryDto);
    }

    public ServiceResponse<CategoryDto> insert(CategoryDto category) {
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByClientId(category.clientId);

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
        List<Category> categoryList = this.categoryRepository.getAllCategoriesByClientId(category.clientId);

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
