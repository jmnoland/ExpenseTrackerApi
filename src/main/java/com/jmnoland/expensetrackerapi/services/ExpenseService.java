package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.helpers.ExpenseAmountHelper;
import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.LineItemRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.ExpenseMapper;
import com.jmnoland.expensetrackerapi.mapping.LineItemMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.LineItemDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import com.jmnoland.expensetrackerapi.models.entities.LineItem;
import com.jmnoland.expensetrackerapi.models.requests.BulkCreateUpdateExpenseRequest;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateLineItemRequest;
import com.jmnoland.expensetrackerapi.models.responses.ExpenseActionResponse;
import com.jmnoland.expensetrackerapi.models.responses.LineItemActionResponse;
import com.jmnoland.expensetrackerapi.validators.expense.CreateExpenseValidator;
import com.jmnoland.expensetrackerapi.validators.expense.UpdateExpenseValidator;
import com.jmnoland.expensetrackerapi.validators.linitems.LineItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService implements ExpenseServiceInterface {

    private final ExpenseRepositoryInterface expenseRepository;
    private final CategoryRepositoryInterface categoryRepository;
    private final PaymentTypeRepositoryInterface paymentTypeRepository;
    private final LineItemRepositoryInterface lineItemRepository;
    private final ExpenseMapper expenseMapper;
    private final LineItemMapper lineItemMapper;
    private final DateProviderInterface dateProvider;

    @Autowired
    public ExpenseService(ExpenseRepositoryInterface expenseRepository,
                          CategoryRepositoryInterface categoryRepository,
                          PaymentTypeRepositoryInterface paymentTypeRepository,
                          LineItemRepositoryInterface lineItemRepository,
                          ExpenseMapper expenseMapper,
                          LineItemMapper lineItemMapper,
                          DateProviderInterface dateProvider) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.lineItemRepository = lineItemRepository;
        this.expenseMapper = expenseMapper;
        this.lineItemMapper = lineItemMapper;
        this.dateProvider = dateProvider;
    }

    public ServiceResponse<List<ExpenseDto>> getExpenses(String clientId) {
        List<Expense> existingExpenses = this.expenseRepository.getExpenses(clientId);
        List<ExpenseDto> list = this.expenseMapper.entityToDto(existingExpenses);

        return new ServiceResponse<>(list, true);
    }

    public ExpenseActionResponse createExpense(CreateUpdateExpenseRequest payload) {
        Date useDate = payload.date != null ? payload.date : dateProvider.getDateNow().getTime();
        String expenseId = UUID.randomUUID().toString();

        float totalAmount = payload.amount;
        if (payload.lineItems != null && !payload.lineItems.isEmpty()) {
            totalAmount = ExpenseAmountHelper.calculateExpenseAmountFromLineItems(payload.lineItems);
        }

        ExpenseDto expenseDto = new ExpenseDto(
                expenseId,
                payload.clientId,
                payload.categoryId,
                payload.paymentTypeId,
                payload.name,
                useDate,
                totalAmount,
                payload.recurringExpenseId
        );

        ServiceResponse<ExpenseDto> insertResult = insert(expenseDto);

        if (!insertResult.successful)
            return this.expenseMapper.dtoToResponse(expenseDto, null, insertResult.validationErrors);

        if (payload.lineItems == null)
            return this.expenseMapper.dtoToResponse(expenseDto, null, insertResult.validationErrors);

        List<LineItemDto> lineItems = new ArrayList<>();
        for (CreateUpdateLineItemRequest request : payload.lineItems) {
            lineItems.add(new LineItemDto(UUID.randomUUID().toString(), expenseId, request.name, request.amount, request.quantity));
        }
        List<LineItemActionResponse> lineItemResponses = createLineItems(lineItems);
        return this.expenseMapper.dtoToResponse(expenseDto, lineItemResponses, insertResult.validationErrors);
    }

    public List<LineItemActionResponse> createLineItems(List<LineItemDto> lineItemDtos) {
        List<LineItemActionResponse> response = new ArrayList<>();
        LineItemValidator validator = new LineItemValidator();
        for (LineItemDto lineItem : lineItemDtos) {
            List<ValidationError> results = validator.validate(lineItem);

            LineItem newLineItem;
            if (results.isEmpty()) {
                newLineItem = this.lineItemMapper.dtoToEntity(lineItem);
                this.lineItemRepository.insert(newLineItem);
            }
            response.add(this.lineItemMapper.dtoToResponse(lineItem, results));
        }
        return response;
    }

    public List<ExpenseActionResponse> createBulkExpense(BulkCreateUpdateExpenseRequest payload) {
        List<ExpenseActionResponse> response = new ArrayList<>();

        for (CreateUpdateExpenseRequest request : payload.expenseRequestList) {
            response.add(createExpense(request));
        }
        return response;
    }

    public ServiceResponse<ExpenseDto> insert(ExpenseDto expense) {
        boolean categoryExists = this.categoryRepository.categoryExists(expense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExistsId(expense.paymentTypeId);

        List<ValidationError> validationErrors = new CreateExpenseValidator()
                .validate(expense, categoryExists, paymentTypeExists);

        Expense newExpense = null;
        if (validationErrors.isEmpty()) {
            newExpense = this.expenseMapper.dtoToEntity(expense);
            try {
                this.expenseRepository.insert(newExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false);
            }
        }

        return new ServiceResponse<>(
                this.expenseMapper.entityToDto(newExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public ServiceResponse<List<ExpenseDto>> bulkInsert(List<ExpenseDto> expenseDtos) {
        List<ExpenseDto> responseList = new ArrayList<>();
        List<ValidationError> responseValidationErrors = new ArrayList<>();

        for (ExpenseDto expense: expenseDtos) {
            ServiceResponse<ExpenseDto> response = this.insert(expense);
            responseValidationErrors.addAll(response.validationErrors);
        }

        return new ServiceResponse<>(
                responseList,
                responseValidationErrors.isEmpty(),
                responseValidationErrors
        );
    }

    public void delete(String expenseId, String clientId) {
        Optional<Expense> existing = this.expenseRepository.getExpense(expenseId, clientId);
        if (!existing.isPresent()) return;
        Expense test = existing.get();
        this.expenseRepository.delete(test);
    }

    public ExpenseActionResponse updateExpense(CreateUpdateExpenseRequest payload) {
        Date useDate = payload.date != null ? payload.date : dateProvider.getDateNow().getTime();
        ExpenseDto expenseDto = new ExpenseDto(
                payload.expenseId,
                payload.clientId,
                payload.categoryId,
                payload.paymentTypeId,
                payload.name,
                useDate,
                payload.amount,
                null
        );
        ServiceResponse<ExpenseDto> updateResponse = update(expenseDto);
        return this.expenseMapper.dtoToResponse(expenseDto, null, updateResponse.validationErrors);
    }

    public ServiceResponse<ExpenseDto> update(ExpenseDto expense) {
        boolean expenseExists = this.expenseRepository.expenseExists(expense.expenseId);
        boolean categoryExists = this.categoryRepository.categoryExists(expense.categoryId);
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExistsId(expense.paymentTypeId);

        List<ValidationError> validationErrors = new UpdateExpenseValidator()
                .validate(expense, expenseExists, categoryExists, paymentTypeExists);

        Expense updatedExpense = null;
        if (validationErrors.isEmpty()) {
            updatedExpense = this.expenseMapper.dtoToEntity(expense);
            try {
                this.expenseRepository.update(updatedExpense);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false);
            }
        }

        return new ServiceResponse<>(
                this.expenseMapper.entityToDto(updatedExpense),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}
