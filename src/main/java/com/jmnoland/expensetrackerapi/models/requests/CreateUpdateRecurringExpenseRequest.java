package com.jmnoland.expensetrackerapi.models.requests;

import com.jmnoland.expensetrackerapi.models.enums.Frequency;
import com.mongodb.lang.Nullable;

import java.util.Date;

public class CreateUpdateRecurringExpenseRequest {
    @Nullable
    public String recurringExpenseId;
    @Nullable
    public String clientId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date startDate;
    @Nullable
    public Date endDate;
    public Float amount;
    @Nullable
    public Date lastExpenseDate;
    public Frequency frequency;
    public CreateUpdateRecurringExpenseRequest() {}
}
