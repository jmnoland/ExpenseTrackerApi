package com.jmnoland.expensetrackerapi.validators.category;

import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CreateCategoryValidator {

    public List<ValidationError> validate(CategoryDto category, List<Category> existingList) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (category.name == null) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Category name is required"
            ));
        }
        for(Category existing : existingList) {
            if (existing.getName().equals(category.name)) {
                validationErrors.add(new ValidationError(
                        "Name",
                        "A category with this name already exists"
                ));
            }
        }
        return validationErrors;
    }
}
