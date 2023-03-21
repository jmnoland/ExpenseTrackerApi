package com.jmnoland.expensetrackerapi.validators.linitems;

import com.jmnoland.expensetrackerapi.models.dtos.LineItemDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class LineItemValidator {
    public LineItemValidator() {}

    public List<ValidationError> validate(LineItemDto lineItem) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (lineItem.expenseId == null) {
            validationErrors.add(new ValidationError("expenseId", "Expense Id cannot be null"));
        }
        if (lineItem.name == null) {
            validationErrors.add(new ValidationError("name", "Name cannot be null"));
        }
        if (lineItem.amount < 0) {
            validationErrors.add(new ValidationError("amount", "Amount cannot be less than 0"));
        }
        if (lineItem.quantity <= 0) {
            validationErrors.add(new ValidationError("quantity", "Quantity must be larger than 0"));
        }
        return validationErrors;
    }
}
