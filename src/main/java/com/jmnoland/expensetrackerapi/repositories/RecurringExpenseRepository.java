package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.RecurringExpenseDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.RecurringExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecurringExpenseRepository implements RecurringExpenseRepositoryInterface {

    private final RecurringExpenseDAO recurringExpenseDAO;

    @Autowired
    public RecurringExpenseRepository(RecurringExpenseDAO recurringExpenseDAO) {
        this.recurringExpenseDAO = recurringExpenseDAO;
    }

    public List<RecurringExpense> getRecurringExpenses(String userId) {
        return this.recurringExpenseDAO.findRecurringExpensesByUserId(userId);
    }

    public boolean recurringExpenseExists(String recurringExpenseId) {
        return this.recurringExpenseDAO.existsById(recurringExpenseId);
    }

    public void insert(RecurringExpense recurringExpense) {
        this.recurringExpenseDAO.insert(recurringExpense);
    }

    public void update(RecurringExpense recurringExpense) {
        this.recurringExpenseDAO.save(recurringExpense);
    }

    public void delete(RecurringExpense recurringExpense) {
        this.recurringExpenseDAO.delete(recurringExpense);
    }
}
