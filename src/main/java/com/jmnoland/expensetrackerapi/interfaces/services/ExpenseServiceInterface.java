package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.BulkCreateUpdateExpenseRequest;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;
import com.jmnoland.expensetrackerapi.models.responses.ExpenseActionResponse;

import java.util.List;

public interface ExpenseServiceInterface {

    ServiceResponse<List<ExpenseDto>> getExpenses(String clientId);

    ExpenseActionResponse createExpense(CreateUpdateExpenseRequest payload);

    List<ExpenseActionResponse> createBulkExpense(BulkCreateUpdateExpenseRequest payload);

    ServiceResponse<ExpenseDto> insert(ExpenseDto expense);

    ServiceResponse<List<ExpenseDto>> bulkInsert(List<ExpenseDto> expenseDtos);

    void delete(String expenseId, String clientId);

    ExpenseActionResponse updateExpense(CreateUpdateExpenseRequest payload);

    ServiceResponse<ExpenseDto> update(ExpenseDto expense);
}
