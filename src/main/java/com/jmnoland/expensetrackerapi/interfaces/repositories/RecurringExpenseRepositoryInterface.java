package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;

import java.util.List;

public interface RecurringExpenseRepositoryInterface {

    List<RecurringExpense> getRecurringExpenses(String userId);

    boolean recurringExpenseExists(String recurringExpenseId);

    void insert(RecurringExpense recurringExpense);

    void update(RecurringExpense recurringExpense);

    void delete(RecurringExpense recurringExpense);
}
