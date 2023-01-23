package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.List;

public interface ExpenseServiceInterface {

    ServiceResponse<List<ExpenseDto>> getExpenses(String clientId);

    ServiceResponse<ExpenseDto> insert(ExpenseDto expense);

    void delete(String expenseId, String clientId);

    ServiceResponse<ExpenseDto> update(ExpenseDto expense);
}
