package com.jmnoland.expensetrackerapi.models.dtos;

import com.mongodb.lang.Nullable;

import java.util.Date;

public class ExpenseDto {
    public String expenseId;
    public String clientId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date date;
    public Float amount;
    @Nullable
    public String recurringExpenseId;

    public ExpenseDto(
            String expenseId,
            String clientId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date date,
            Float amount,
            @Nullable
            String recurringExpenseId
    ) {
        this.expenseId = expenseId;
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.recurringExpenseId = recurringExpenseId;
    }
}
