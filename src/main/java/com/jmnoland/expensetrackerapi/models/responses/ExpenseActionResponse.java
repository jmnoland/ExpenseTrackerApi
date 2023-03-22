package com.jmnoland.expensetrackerapi.models.responses;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.mongodb.lang.Nullable;

import java.util.Date;
import java.util.List;

public class ExpenseActionResponse {
    public String expenseId;
    public String clientId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date date;
    public Float amount;
    @Nullable
    public String recurringExpenseId;
    @Nullable
    public List<LineItemActionResponse> lineItems;

    public List<ValidationError> validationErrors;
}
