package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.helpers.RequestHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateRecurringExpenseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/recurringexpense")
public class RecurringExpenseController {

    private final HttpServletRequest request;
    private final RecurringExpenseServiceInterface recurringExpenseService;

    @Autowired
    public RecurringExpenseController(HttpServletRequest request, RecurringExpenseServiceInterface recurringExpenseService) {
        this.request = request;
        this.recurringExpenseService = recurringExpenseService;
    }

    @GetMapping()
    public ServiceResponse<List<RecurringExpenseDto>> getRecurringExpenses() {
        String clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.recurringExpenseService.getRecurringExpenses(clientId);
    }

    @PostMapping()
    public ServiceResponse<RecurringExpenseDto> createRecurringExpense(@RequestBody CreateUpdateRecurringExpenseRequest request) {
        request.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.recurringExpenseService.createRecurringExpense(request);
    }

    @PatchMapping()
    public ServiceResponse<RecurringExpenseDto> updateRecurringExpense(@RequestBody CreateUpdateRecurringExpenseRequest request) {
        request.clientId = RequestHelper.getClientIdFromHeader(this.request);
        return this.recurringExpenseService.updateRecurringExpense(request);
    }

    @DeleteMapping()
    public void deleteRecurringExpense(@RequestBody RecurringExpenseDto expense) {
        expense.clientId = RequestHelper.getClientIdFromHeader(this.request);
        this.recurringExpenseService.delete(expense);
    }
}
