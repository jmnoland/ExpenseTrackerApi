package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("lineitems")
public class LineItem {
    @Id
    private String lineItemId;
    private String expenseId;
    private String name;
    private Float amount;
    private Integer quantity;

    public LineItem(String lineItemId, String expenseId, String name, Float amount, Integer quantity) {
        this.lineItemId = lineItemId;
        this.expenseId = expenseId;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
}
