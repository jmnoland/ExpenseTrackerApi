package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("recurringexpenses")
public class RecurringExpense {

    @Id
    private final String recurringExpenseId;
    private String userId;
    private String categoryId;
    private String paymentTypeId;
    private String name;
    private Date startDate;
    private Date endDate;
    private String frequency;
    private Float amount;

    public String getRecurringExpenseId() {
        return recurringExpenseId;
    }
    public String getUserId() {
        return userId;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public String getPaymentTypeId() {
        return paymentTypeId;
    }
    public String getName() {
        return name;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public String getFrequency() {
        return frequency;
    }
    public Float getAmount() {
        return amount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public RecurringExpense(
            String recurringExpenseId,
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date startDate,
            Date endDate,
            String frequency,
            Float amount) {
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
