package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.helpers.RequestHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final HttpServletRequest request;
    private final ExpenseServiceInterface expenseService;

    @Autowired
    public ExpenseController(HttpServletRequest request, ExpenseServiceInterface expenseService) {
        this.request = request;
        this.expenseService = expenseService;
    }

    @GetMapping()
    public ServiceResponse<List<ExpenseDto>> getExpenses() {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.expenseService.getExpenses(clientId);
    }

    @PostMapping()
    public ServiceResponse<ExpenseDto> createExpense(@RequestBody ExpenseDto expense) {
        expense.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.expenseService.insert(expense);
    }

    @PatchMapping()
    public ServiceResponse<ExpenseDto> updateExpense(@RequestBody ExpenseDto expense) {
        expense.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.expenseService.update(expense);
    }

    @DeleteMapping()
    public void deleteExpense(String expenseId) {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        this.expenseService.delete(expenseId, clientId);
    }
}
