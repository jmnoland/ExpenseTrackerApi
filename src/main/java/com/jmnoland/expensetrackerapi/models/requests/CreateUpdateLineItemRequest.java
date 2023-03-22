package com.jmnoland.expensetrackerapi.models.requests;

import com.mongodb.lang.Nullable;

public class CreateUpdateLineItemRequest {
    @Nullable
    public String expenseId;
    @Nullable
    public String lineItemId;
    public String name;
    public Float amount;
    public Integer quantity;
}
