package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.List;

public interface RecurringExpenseServiceInterface {

    ServiceResponse<List<RecurringExpenseDto>> getRecurringExpenses(String clientId);

    ServiceResponse<RecurringExpenseDto> insert(RecurringExpenseDto recurringExpense);

    void delete(RecurringExpenseDto recurringExpense);

    ServiceResponse<RecurringExpenseDto> update(RecurringExpenseDto recurringExpense);
}
