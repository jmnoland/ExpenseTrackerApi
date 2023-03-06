package com.jmnoland.expensetrackerapi.models.requests;

import com.mongodb.lang.Nullable;

import java.util.Date;

public class CreateUpdateExpenseRequest {
    @Nullable
    public String expenseId;
    @Nullable
    public String clientId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    @Nullable
    public Date date;
    public Float amount;
    @Nullable
    public String recurringExpenseId;
    public CreateUpdateExpenseRequest() {}
}
