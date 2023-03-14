package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateRecurringExpenseRequest;

import java.util.List;

public interface RecurringExpenseServiceInterface {

    ServiceResponse<List<RecurringExpenseDto>> getRecurringExpenses(String clientId);

    ServiceResponse<RecurringExpenseDto> insert(RecurringExpenseDto recurringExpense);

    ServiceResponse<RecurringExpenseDto> createRecurringExpense(CreateUpdateRecurringExpenseRequest request);

    void delete(RecurringExpenseDto recurringExpense);

    ServiceResponse<RecurringExpenseDto> update(RecurringExpenseDto recurringExpense);

    ServiceResponse<RecurringExpenseDto> updateRecurringExpense(CreateUpdateRecurringExpenseRequest request);

}
