package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryInterface {

    List<Expense> getExpenses(String clientId);

    boolean expenseExists(String expenseId);

    Optional<Expense> getExpense(String expenseId, String clientId);

    void insert(Expense expense);

    void update(Expense expense);

    void delete(Expense expense);
}
