package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.List;

public interface ExpenseServiceInterface {

    List<ExpenseDto> getExpenses(String userId);

    ServiceResponse<ExpenseDto> insert(ExpenseDto expense);

    void delete(String expense);

    ServiceResponse<ExpenseDto> update(ExpenseDto expense);
}
