package com.jmnoland.expensetrackerapi.models.requests;

import com.mongodb.lang.Nullable;

public class ConfirmExpenseRequest {
    public String expenseId;
    @Nullable
    public String clientId;
    public Float amount;
    public ConfirmExpenseRequest() {}
}
