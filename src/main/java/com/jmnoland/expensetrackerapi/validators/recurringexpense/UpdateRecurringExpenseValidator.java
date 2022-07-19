package com.jmnoland.expensetrackerapi.validators.recurringexpense;

import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class UpdateRecurringExpenseValidator {

    public UpdateRecurringExpenseValidator() {}

    public List<ValidationError> validate(RecurringExpenseDto recurringExpense,
                                          boolean recurringExpenseExists,
                                          boolean categoryExists,
                                          boolean paymentTypeExists,
                                          boolean userExists) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (recurringExpense.recurringExpenseId == null) {
            validationErrors.add(new ValidationError(
                    "RecurringExpenseId",
                    "RecurringExpenseId is required"
            ));
        } else if(!recurringExpenseExists) {
            validationErrors.add(new ValidationError(
                    "RecurringExpenseId",
                    "Recurring expense must exist"
            ));
        }

        if (recurringExpense.userId == null) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "The user is required"
            ));
        } else if (!userExists) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "User does not exist"
            ));
        }

        if (recurringExpense.categoryId == null) {
            validationErrors.add(new ValidationError(
                    "CategoryId",
                    "The category is required"
            ));
        } else if (!categoryExists) {
            validationErrors.add(new ValidationError(
                    "CategoryId",
                    "Category does not exist"
            ));
        }

        if (recurringExpense.paymentTypeId == null) {
            validationErrors.add(new ValidationError(
                    "PaymentTypeId",
                    "The payment type is required"
            ));
        } else if (!paymentTypeExists) {
            validationErrors.add(new ValidationError(
                    "PaymentTypeId",
                    "Payment type does not exist"
            ));
        }

        if (recurringExpense.amount == null) {
            validationErrors.add(new ValidationError(
                    "Amount",
                    "The amount is required"
            ));
        }
        if (recurringExpense.name == null || recurringExpense.name.equals("")) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Name is required"
            ));
        }
        if (recurringExpense.startDate == null) {
            validationErrors.add(new ValidationError(
                    "StartDate",
                    "The date is required"
            ));
        }

        if (recurringExpense.endDate != null && recurringExpense.startDate != null) {
            int compareResult = recurringExpense.endDate.compareTo(recurringExpense.startDate);
            if (compareResult < 0) {
                validationErrors.add(new ValidationError(
                        "EndDate",
                        "End date must be after or same as start date"
                ));
            }
        }
        return validationErrors;
    }
}
