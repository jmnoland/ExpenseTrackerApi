package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.RecurringExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
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
    private final UserRepositoryInterface userRepositoryInterface;
    private final RecurringExpenseMapper mapper;
    @Autowired
    public RecurringExpenseService(RecurringExpenseRepositoryInterface recurringExpenseRepository,
                                   CategoryRepositoryInterface categoryRepositoryInterface,
                                   PaymentTypeRepositoryInterface paymentTypeRepositoryInterface,
                                   UserRepositoryInterface userRepositoryInterface,
                                   RecurringExpenseMapper recurringExpenseMapper) {
        this.recurringExpenseRepository = recurringExpenseRepository;
        this.categoryRepositoryInterface =categoryRepositoryInterface;
        this.paymentTypeRepositoryInterface = paymentTypeRepositoryInterface;
        this.userRepositoryInterface = userRepositoryInterface;
        this.mapper = recurringExpenseMapper;
    }

    public List<RecurringExpenseDto> getRecurringExpenses(String userId) {
        List<RecurringExpense> expenses = this.recurringExpenseRepository.getRecurringExpenses(userId);
        return this.mapper.entityToDto(expenses);
    }

    public ServiceResponse<RecurringExpenseDto> insert(RecurringExpenseDto recurringExpense) {
        boolean categoryExists = this.categoryRepositoryInterface.categoryExists(recurringExpense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepositoryInterface.paymentTypeExistsId(recurringExpense.paymentTypeId);
        boolean userExists = this.userRepositoryInterface.userExists(recurringExpense.userId);

        List<ValidationError> validationErrors = new CreateRecurringExpenseValidator()
                .validate(recurringExpense, categoryExists, paymentTypeExists, userExists);

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
        boolean userExists = this.userRepositoryInterface.userExists(recurringExpense.userId);

        List<ValidationError> validationErrors = new UpdateRecurringExpenseValidator()
                .validate(recurringExpense, recurringExpenseExists, categoryExists, paymentTypeExists, userExists);

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