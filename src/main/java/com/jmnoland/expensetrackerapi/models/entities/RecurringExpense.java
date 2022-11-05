package com.jmnoland.expensetrackerapi.models.entities;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("recurringexpenses")
public class RecurringExpense {

    @Id
    private final String recurringExpenseId;
    private String userId;
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
            String userId,
            String categoryId,
            String paymentTypeId,
            String name,
            Date startDate,
            @Nullable Date endDate,
            String frequency,
            Float amount,
            @Nullable Date lastExpenseDate) {
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
