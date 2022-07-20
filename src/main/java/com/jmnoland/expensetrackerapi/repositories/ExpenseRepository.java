package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.ExpenseDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpenseRepository implements ExpenseRepositoryInterface {

    private final ExpenseDAO expenseDao;

    @Autowired
    public ExpenseRepository(ExpenseDAO expenseDao) {
        this.expenseDao = expenseDao;
    }

    @Override
    public List<Expense> getExpenses(String userId) {
        return null;
    }

    @Override
    public boolean expenseExists(String expenseId) {
        return false;
    }

    @Override
    public Expense getExpense(String expenseId) {
        return null;
    }

    @Override
    public void insert(Expense expense) {

    }

    @Override
    public void update(Expense expense) {

    }

    @Override
    public void delete(Expense expense) {

    }
}
