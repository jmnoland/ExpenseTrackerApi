package com.jmnoland.expensetrackerapi.validators.expense;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import com.mongodb.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConfirmExpenseValidator {

    public List<ValidationError> validate(Float amount, @Nullable Expense existing) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (amount == null) {
            validationErrors.add(new ValidationError(
                    "Amount",
                    "The amount is required"
            ));
        }

        if (existing == null) {
            validationErrors.add(new ValidationError(
                    "ExpenseId",
                    "Expense does not exist"
            ));
        } else if (existing.getConfirmed()) {
            validationErrors.add(new ValidationError(
                    "ExpenseId",
                    "Expense is already confirmed"
            ));
        }
        return validationErrors;
    }
}
