package com.jmnoland.expensetrackerapi.models.responses;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.mongodb.lang.Nullable;

import java.util.List;

public class LineItemActionResponse {
    public String lineItemId;
    public String expenseId;
    public String name;
    public Float amount;
    public Integer quantity;

    @Nullable
    public List<ValidationError> validationErrors;
}
