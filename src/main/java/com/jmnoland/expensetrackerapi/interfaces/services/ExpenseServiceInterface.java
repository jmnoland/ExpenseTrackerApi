package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.BulkCreateUpdateExpenseRequest;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;

import java.util.List;

public interface ExpenseServiceInterface {

    ServiceResponse<List<ExpenseDto>> getExpenses(String clientId);

    ServiceResponse<ExpenseDto> createExpense(CreateUpdateExpenseRequest payload);

    ServiceResponse<List<ExpenseDto>> createBulkExpense(BulkCreateUpdateExpenseRequest payload);

    ServiceResponse<ExpenseDto> insert(ExpenseDto expense);

    ServiceResponse<List<ExpenseDto>> bulkInsert(List<ExpenseDto> expenseDtos);

    void delete(String expenseId, String clientId);

    ServiceResponse<ExpenseDto> updateExpense(CreateUpdateExpenseRequest payload);

    ServiceResponse<ExpenseDto> update(ExpenseDto expense);
}
