package com.jmnoland.expensetrackerapi.models.entities;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("recurringexpenses")
public class RecurringExpense {

    @Id
    private final String recurringExpenseId;
    private String clientId;
    private String categoryId;
    private String paymentTypeId;
    private String name;
    private Date startDate;
    @Nullable
    private Date endDate;
    private String frequency;
    private Float amount;
    @Nullable
    private Date lastExpenseDate;

    public RecurringExpense(
            String recurringExpenseId,
            String clientId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date startDate,
            @Nullable Date endDate,
            String frequency,
            Float amount,
            @Nullable Date lastExpenseDate) {
        this.recurringExpenseId = recurringExpenseId;
        this.clientId = clientId;
        this.categoryId = categoryId;
        this.paymentTypeId = paymentTypeId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.amount = amount;
        this.lastExpenseDate = lastExpenseDate;
    }

    public String getRecurringExpenseId() { return this.recurringExpenseId; }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable Date endDate) {
        this.endDate = endDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Nullable
    public Date getLastExpenseDate() {
        return lastExpenseDate;
    }

    public void setLastExpenseDate(@Nullable Date lastExpenseDate) {
        this.lastExpenseDate = lastExpenseDate;
    }
}
