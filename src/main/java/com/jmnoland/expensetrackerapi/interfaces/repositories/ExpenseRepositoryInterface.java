package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Expense;

import java.util.List;

public interface ExpenseRepositoryInterface {

    List<Expense> getExpenses(String userId);

    boolean expenseExists(String expenseId);

    Expense getExpense(String expenseId);

    void insert(Expense expense);

    void update(Expense expense);

    void delete(Expense expense);
}
