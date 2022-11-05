package com.jmnoland.expensetrackerapi.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("expenses")
public class Expense {

    @Id
    private String expenseId;
    private String name;
    private Float amount;
    private String userId;
    private String categoryId;
    private String paymentTypeId;
    private Date date;
    private String recurringExpenseId;
    public Expense(
            String expenseId,
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date date,
            Float amount,
            String recurringExpenseId) {
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
