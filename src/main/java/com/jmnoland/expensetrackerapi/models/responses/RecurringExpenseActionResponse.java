package com.jmnoland.expensetrackerapi.models.responses;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.enums.Frequency;
import com.mongodb.lang.Nullable;

import java.util.Date;
import java.util.List;

public class RecurringExpenseActionResponse {
    public String recurringExpenseId;
    public String clientId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date startDate;
    @Nullable
    public Date endDate;
    public Frequency frequency;
    public Float amount;
    @Nullable
    public Date lastExpenseDate;

    public List<ValidationError> validationErrors;
}
