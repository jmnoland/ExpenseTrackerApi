package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.UUID;

public class LineItemDto {
    public String lineItemId;
    public String expenseId;
    public String name;
    public Float amount;
    public Integer quantity;

    public LineItemDto() {}
    public LineItemDto(String lineItemId, String expenseId, String name, Float amount, Integer quantity) {
        this.expenseId = expenseId;
        this.lineItemId = lineItemId;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
    }
    public LineItemDto(String expenseId, String name, Float amount, Integer quantity) {
        this.lineItemId = UUID.randomUUID().toString();
        this.expenseId = expenseId;
        this.name = name;
        this.amount = amount;
        this.quantity = quantity;
    }
}
