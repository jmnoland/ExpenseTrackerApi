package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurringexpense")
public class RecurringExpenseController {

    private final RecurringExpenseServiceInterface recurringExpenseService;

    @Autowired
    public RecurringExpenseController(RecurringExpenseServiceInterface recurringExpenseService) {
        this.recurringExpenseService = recurringExpenseService;
    }

    @GetMapping()
    public ServiceResponse<List<RecurringExpenseDto>> getRecurringExpenses(String clientId) {
        return this.recurringExpenseService.getRecurringExpenses(clientId);
    }

    @PostMapping()
    public ServiceResponse<RecurringExpenseDto> createRecurringExpense(@RequestBody RecurringExpenseDto expense) {
        return this.recurringExpenseService.insert(expense);
    }

    @PatchMapping()
    public ServiceResponse<RecurringExpenseDto> updateRecurringExpense(@RequestBody RecurringExpenseDto expense) {
        return this.recurringExpenseService.update(expense);
    }

    @DeleteMapping()
    public void deleteRecurringExpense(@RequestBody RecurringExpenseDto expense) {
        this.recurringExpenseService.delete(expense);
    }
}
