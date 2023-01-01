package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.ExpenseDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseRepository implements ExpenseRepositoryInterface {

    private final ExpenseDAO expenseDao;

    @Autowired
    public ExpenseRepository(ExpenseDAO expenseDao) {
        this.expenseDao = expenseDao;
    }

    public List<Expense> getExpenses(String clientId) {
        return this.expenseDao.findExpensesByClientId(clientId);
    }

    public boolean expenseExists(String expenseId) {
        return this.expenseDao.existsById(expenseId);
    }

    public Optional<Expense> getExpense(String expenseId) {
        return this.expenseDao.findById(expenseId);
    }

    public void insert(Expense expense) {
        this.expenseDao.insert(expense);
    }

    public void update(Expense expense) {
        this.expenseDao.save(expense);
    }

    public void delete(Expense expense) {
        this.expenseDao.delete(expense);
    }
}
