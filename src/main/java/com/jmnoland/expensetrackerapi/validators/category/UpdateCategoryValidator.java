package com.jmnoland.expensetrackerapi.validators.category;

import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateCategoryValidator {

    public List<ValidationError> validate(CategoryDto category, List<Category> existingList) {
        List<ValidationError> validationErrors = new ArrayList<>();
        boolean categoryExists = false;
        if (category.name == null) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Category name is required"
            ));
        }
        for(Category existing : existingList) {
            if (Objects.equals(existing.getCategoryId(), category.categoryId)) categoryExists = true;
            if (existing.getName().equals(category.name)) {
                validationErrors.add(new ValidationError(
                        "Name",
                        "A category with this name already exists"
                ));
            }
        }
        if (!categoryExists) {
            validationErrors.add(new ValidationError(
                    "CategoryId",
                    "This category does not exist"
            ));
        }
        return validationErrors;
    }
}
