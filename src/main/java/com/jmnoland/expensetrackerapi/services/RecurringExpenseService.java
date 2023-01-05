package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.RecurringExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.RecurringExpenseMapper;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;
import com.jmnoland.expensetrackerapi.validators.recurringexpense.CreateRecurringExpenseValidator;
import com.jmnoland.expensetrackerapi.validators.recurringexpense.UpdateRecurringExpenseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurringExpenseService implements RecurringExpenseServiceInterface {

    private final RecurringExpenseRepositoryInterface recurringExpenseRepository;
    private final CategoryRepositoryInterface categoryRepositoryInterface;
    private final PaymentTypeRepositoryInterface paymentTypeRepositoryInterface;
    private final RecurringExpenseMapper mapper;
    @Autowired
    public RecurringExpenseService(RecurringExpenseRepositoryInterface recurringExpenseRepository,
                                   CategoryRepositoryInterface categoryRepositoryInterface,
                                   PaymentTypeRepositoryInterface paymentTypeRepositoryInterface,
                                   RecurringExpenseMapper recurringExpenseMapper) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.categoryRepositoryInterface =categoryRepositoryInterface;
        this.paymentTypeRepositoryInterface = paymentTypeRepositoryInterface;
        this.mapper = recurringExpenseMapper;
    }

    public ServiceResponse<List<RecurringExpenseDto>> getRecurringExpenses(String clientId) {
        List<RecurringExpense> expenses = this.recurringExpenseRepository.getRecurringExpenses(clientId);
        List<RecurringExpenseDto> list = this.mapper.entityToDto(expenses);

        return new ServiceResponse<>(list, true, null);
    }

    public ServiceResponse<RecurringExpenseDto> insert(RecurringExpenseDto recurringExpense) {
        boolean categoryExists = this.categoryRepositoryInterface.categoryExists(recurringExpense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepositoryInterface.paymentTypeExistsId(recurringExpense.paymentTypeId);

        List<ValidationError> validationErrors = new CreateRecurringExpenseValidator()
                .validate(recurringExpense, categoryExists, paymentTypeExists);

        RecurringExpense newRecurringExpense = null;
        if (validationErrors.isEmpty()) {
            newRecurringExpense = this.mapper.dtoToEntity(recurringExpense);
            try {
                this.recurringExpenseRepository.insert(newRecurringExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newRecurringExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public void delete(RecurringExpenseDto recurringExpense) {
        RecurringExpense existing = this.mapper.dtoToEntity(recurringExpense);
        this.recurringExpenseRepository.insert(existing);
    }

    public ServiceResponse<RecurringExpenseDto> update(RecurringExpenseDto recurringExpense) {
        boolean recurringExpenseExists = this.recurringExpenseRepository.recurringExpenseExists(recurringExpense.recurringExpenseId);
        boolean categoryExists = this.categoryRepositoryInterface.categoryExists(recurringExpense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepositoryInterface.paymentTypeExistsId(recurringExpense.paymentTypeId);

        List<ValidationError> validationErrors = new UpdateRecurringExpenseValidator()
                .validate(recurringExpense, recurringExpenseExists, categoryExists, paymentTypeExists);

        RecurringExpense updatedRecurringExpense = null;
        if (validationErrors.isEmpty()) {
            updatedRecurringExpense = this.mapper.dtoToEntity(recurringExpense);
            try {
                this.recurringExpenseRepository.update(updatedRecurringExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(updatedRecurringExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}