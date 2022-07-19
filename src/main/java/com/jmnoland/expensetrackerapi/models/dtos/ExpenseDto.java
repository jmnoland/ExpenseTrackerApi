package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class ExpenseDto {
    public String expenseId;
    public String userId;
    public String categoryId;
    public String paymentTypeId;
    public String name;
    public Date date;
    public Float amount;
    public Long recurringExpenseId;

    public ExpenseDto(
            String expenseId,
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date date,
            Float amount,
            Long recurringExpenseId
    ) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.recurringExpenseId = recurringExpenseId;
    }
}
