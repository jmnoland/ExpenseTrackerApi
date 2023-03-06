package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SchedulerService {

    private final RecurringExpenseServiceInterface recurringExpenseService;
    private final ExpenseServiceInterface expenseService;
    private final ApiKeyRepositoryInterface apiKeyRepository;
    private final DateProviderInterface dateProvider;

    public SchedulerService(RecurringExpenseServiceInterface recurringExpenseService,
                            ExpenseServiceInterface expenseService,
                            ApiKeyRepositoryInterface apiKeyRepository,
                            DateProviderInterface dateProvider) {
        this.recurringExpenseService = recurringExpenseService;
        this.expenseService = expenseService;
        this.apiKeyRepository = apiKeyRepository;
        this.dateProvider = dateProvider;
    }
    @Scheduled(cron = "0 12 * * * *")
    public void scheduleTask()
    {
        List<String> clientIdList = this.apiKeyRepository.getAllClientIds();
        for(String id : clientIdList) {

            ServiceResponse<List<RecurringExpenseDto>> response = this.recurringExpenseService.getRecurringExpenses(id);
            if (!response.successful) continue;

            List<CreateUpdateExpenseRequest> expenseRequests = CreatePendingExpenses(response.responseObject);
            for(CreateUpdateExpenseRequest request : expenseRequests) {
                this.expenseService.createExpense(request);
            }
        }
    }

    public List<CreateUpdateExpenseRequest> CreatePendingExpenses(List<RecurringExpenseDto> recurExpenseList) {
        List<CreateUpdateExpenseRequest> expenseRequests = new ArrayList<>();
        for(RecurringExpenseDto recurringExpense : recurExpenseList) {
            ServiceResponse<CreateUpdateExpenseRequest> newExpense = GenerateExpenseForDate(recurringExpense);
            if (newExpense.successful) {
                expenseRequests.add(newExpense.responseObject);
                this.recurringExpenseService.update(recurringExpense);
            }
        }
        return expenseRequests;
    }

    public ServiceResponse<CreateUpdateExpenseRequest> GenerateExpenseForDate(RecurringExpenseDto recurringExpense) {
        Calendar currentTime = this.dateProvider.getDateNow();
        CreateUpdateExpenseRequest expenseRequest = null;

        if (recurringExpense.lastExpenseDate == null) {
            if (currentTime.getTime().after(recurringExpense.startDate)
                    || currentTime.getTime().equals(recurringExpense.startDate)) {
                recurringExpense.lastExpenseDate = currentTime.getTime();

                expenseRequest = CreateExpenseRequest(recurringExpense, currentTime);
            }
        } else {
            Calendar lastExpenseDate = new GregorianCalendar();
            lastExpenseDate.setTime(recurringExpense.lastExpenseDate);
            lastExpenseDate.set(Calendar.MINUTE, 0);
            lastExpenseDate.set(Calendar.HOUR, 0);
            lastExpenseDate.set(Calendar.SECOND, 0);
            lastExpenseDate.set(Calendar.MILLISECOND, 0);
            Calendar dateBeforeChange = (Calendar) lastExpenseDate.clone();
            switch (recurringExpense.frequency) {
                case MONTHLY:
                    lastExpenseDate.add(Calendar.MONTH, 1);
                    break;
                case WEEKLY:
                    lastExpenseDate.add(Calendar.HOUR, 7 * 24);
                    break;
                case DAILY:
                    lastExpenseDate.add(Calendar.HOUR, 24);
                    break;
                case YEARLY:
                    lastExpenseDate.add(Calendar.YEAR, 1);
                    break;
                default:
                    break;
            }
            if (dateBeforeChange.compareTo(lastExpenseDate) != 0) {
                if (lastExpenseDate.compareTo(currentTime) <= 0) {
                    recurringExpense.lastExpenseDate = lastExpenseDate.getTime();

                    expenseRequest = CreateExpenseRequest(recurringExpense, currentTime);
                }
            }
        }
        return new ServiceResponse<>(expenseRequest, expenseRequest != null, null);
    }

    private CreateUpdateExpenseRequest CreateExpenseRequest(RecurringExpenseDto recurringExpense, Calendar currentTime) {
        CreateUpdateExpenseRequest expenseRequest = new CreateUpdateExpenseRequest();
        expenseRequest.clientId = recurringExpense.clientId;
        expenseRequest.categoryId = recurringExpense.categoryId;
        expenseRequest.paymentTypeId = recurringExpense.paymentTypeId;
        expenseRequest.name = recurringExpense.name;
        expenseRequest.date = currentTime.getTime();
        expenseRequest.amount = recurringExpense.amount;
        expenseRequest.recurringExpenseId = recurringExpense.recurringExpenseId;
        return expenseRequest;
    }
}
