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

    @Override
    public List<RecurringExpense> getRecurringExpenses(String userId) {
        return null;
    }

    @Override
    public boolean recurringExpenseExists(String recurringExpenseId) {
        return false;
    }

    @Override
    public void insert(RecurringExpense recurringExpense) {

    }

    @Override
    public void update(RecurringExpense recurringExpense) {

    }

    @Override
    public void delete(RecurringExpense recurringExpense) {

    }
}
