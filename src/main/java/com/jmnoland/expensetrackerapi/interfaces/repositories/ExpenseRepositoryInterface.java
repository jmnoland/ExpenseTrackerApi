package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Expense;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryInterface {

    List<Expense> getExpenses(String clientId);

    List<Expense> getExpensesDateBetween(String clientId, Date startDate, Date endDate);

    boolean expenseExists(String expenseId);

    Optional<Expense> getExpense(String expenseId, String clientId);

    void insert(Expense expense);

    void update(Expense expense);

    void delete(Expense expense);
}
