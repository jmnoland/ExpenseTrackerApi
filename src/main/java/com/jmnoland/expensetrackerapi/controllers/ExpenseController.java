package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public List<ExpenseDto> getExpenses(String userId) {
        return this.expenseService.getExpenses(userId);
    }

    @PostMapping()
    public ServiceResponse<ExpenseDto> createExpense(@RequestBody ExpenseDto expense) {
        return this.expenseService.insert(expense);
    }

    @PatchMapping()
    public ServiceResponse<ExpenseDto> updateExpense(@RequestBody ExpenseDto expense) {
        return this.expenseService.update(expense);
    }

    @DeleteMapping()
    public void deleteExpense(String expenseId) {
        this.expenseService.delete(expenseId);
    }
}
