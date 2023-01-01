package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.ExpenseMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import com.jmnoland.expensetrackerapi.validators.expense.CreateExpenseValidator;
import com.jmnoland.expensetrackerapi.validators.expense.UpdateExpenseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements ExpenseServiceInterface {

    private final ExpenseRepositoryInterface expenseRepository;
    private final CategoryRepositoryInterface categoryRepositoryInterface;
    private final PaymentTypeRepositoryInterface paymentTypeRepositoryInterface;
    private final ExpenseMapper mapper;

    @Autowired
    public ExpenseService(ExpenseRepositoryInterface expenseRepository,
                          CategoryRepositoryInterface categoryRepositoryInterface,
                          PaymentTypeRepositoryInterface paymentTypeRepositoryInterface,
                          ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.categoryRepositoryInterface = categoryRepositoryInterface;
        this.paymentTypeRepositoryInterface = paymentTypeRepositoryInterface;
        this.mapper = expenseMapper;
    }

    public List<ExpenseDto> getExpenses(String clientId) {
        List<Expense> existingExpenses = this.expenseRepository.getExpenses(clientId);
        return this.mapper.entityToDto(existingExpenses);
    }

    public ServiceResponse<ExpenseDto> insert(ExpenseDto expense) {
        boolean categoryExists = this.categoryRepositoryInterface.categoryExists(expense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepositoryInterface.paymentTypeExistsId(expense.paymentTypeId);

        List<ValidationError> validationErrors = new CreateExpenseValidator()
                .validate(expense, categoryExists, paymentTypeExists);

        Expense newExpense = null;
        if (validationErrors.isEmpty()) {
            newExpense = this.mapper.dtoToEntity(expense);
            try {
                this.expenseRepository.insert(newExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public void delete(String expenseId) {
        Optional<Expense> existing = this.expenseRepository.getExpense(expenseId);
        if (!existing.isPresent()) return;
        Expense test = existing.get();
        this.expenseRepository.delete(test);
    }

    public ServiceResponse<ExpenseDto> update(ExpenseDto expense) {
        boolean expenseExists = this.expenseRepository.expenseExists(expense.expenseId);
        boolean categoryExists = this.categoryRepositoryInterface.categoryExists(expense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepositoryInterface.paymentTypeExists(expense.paymentTypeId);

        List<ValidationError> validationErrors = new UpdateExpenseValidator()
                .validate(expense, expenseExists, categoryExists, paymentTypeExists);

        Expense updatedExpense = null;
        if (validationErrors.isEmpty()) {
            updatedExpense = this.mapper.dtoToEntity(expense);
            try {
                this.expenseRepository.update(updatedExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(updatedExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}
