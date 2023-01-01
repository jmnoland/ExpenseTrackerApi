package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("expenses")
public class Expense {

    @Id
    private String expenseId;
    private String name;
    private Float amount;
    private String clientId;
    private String categoryId;
    private String paymentTypeId;
    private Date date;
    private String recurringExpenseId;
    public Expense(
            String expenseId,
            String clientId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date date,
            Float amount,
            String recurringExpenseId) {
        this.expenseId = expenseId;
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.recurringExpenseId = recurringExpenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRecurringExpenseId() {
        return recurringExpenseId;
    }

    public void setRecurringExpenseId(String recurringExpenseId) {
        this.recurringExpenseId = recurringExpenseId;
    }
}
