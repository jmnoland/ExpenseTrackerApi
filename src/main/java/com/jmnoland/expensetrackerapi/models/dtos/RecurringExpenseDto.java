package com.jmnoland.expensetrackerapi.models.dtos;

import com.jmnoland.expensetrackerapi.models.enums.Frequency;
import com.mongodb.lang.Nullable;

import java.util.Date;

public class RecurringExpenseDto {
    public String recurringExpenseId;
    public String userId;
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

    public RecurringExpenseDto(
            String recurringExpenseId,
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date startDate,
            @Nullable Date endDate,
            Frequency frequency,
            Float amount,
            @Nullable Date lastExpenseDate
    ) {
        this.recurringExpenseId = recurringExpenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.amount = amount;
        this.lastExpenseDate = lastExpenseDate;
    }
}
