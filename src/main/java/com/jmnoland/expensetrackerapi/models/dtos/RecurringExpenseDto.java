package com.jmnoland.expensetrackerapi.models.dtos;

import com.jmnoland.expensetrackerapi.models.enums.Frequency;

import java.util.Date;

public class RecurringExpenseDto {
    public String recurringExpenseId;
    public String userId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date startDate;
    public Date endDate;
    public Frequency frequency;
    public Float amount;

    public RecurringExpenseDto(
            String recurringExpenseId,
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date startDate,
            Date endDate,
            Frequency frequency,
            Float amount
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
    }
}
