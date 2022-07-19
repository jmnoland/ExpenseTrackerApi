package com.jmnoland.expensetrackerapi.validators.expense;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CreateExpenseValidator {

    public CreateExpenseValidator() {}

    public List<ValidationError> validate(ExpenseDto expense,
                                          boolean categoryExists,
                                          boolean paymentTypeExists,
                                          boolean userExists) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (expense.userId == null) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "The user is required"
            ));
        }
        if (expense.categoryId == null) {
            validationErrors.add(new ValidationError(
                    "CategoryId",
                    "The category is required"
            ));
        }
        if (expense.paymentTypeId == null) {
            validationErrors.add(new ValidationError(
                    "PaymentTypeId",
                    "The payment type is required"
            ));
        }
        if (expense.amount == null) {
            validationErrors.add(new ValidationError(
                    "Amount",
                    "The amount is required"
            ));
        }
        if (expense.name == null || expense.name.equals("")) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Name is required"
            ));
        }
        if (expense.date == null) {
            validationErrors.add(new ValidationError(
                    "Date",
                    "The date is required"
            ));
        }

        if (!categoryExists) {
            validationErrors.add(new ValidationError(
                    "CategoryId",
                    "Category does not exist"
            ));
        }

        if (!paymentTypeExists) {
            validationErrors.add(new ValidationError(
                    "PaymentTypeId",
                    "Payment type does not exist"
            ));
        }

        if (!userExists) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "User does not exist"
            ));
        }
        return validationErrors;
    }
}
