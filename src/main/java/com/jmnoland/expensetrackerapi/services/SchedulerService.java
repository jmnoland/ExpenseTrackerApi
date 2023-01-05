package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
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
            List<RecurringExpenseDto> recurExpenseList = response.responseObject;

            List<ExpenseDto> expenseDtos = CreatePendingExpenses(recurExpenseList);
            for(ExpenseDto expenseDto : expenseDtos) {
                this.expenseService.insert(expenseDto);
            }
        }
    }

    public List<ExpenseDto> CreatePendingExpenses(List<RecurringExpenseDto> recurExpenseList) {
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        for(RecurringExpenseDto recurringExpense : recurExpenseList) {
            ExpenseDto newExpense = GenerateExpenseForDate(recurringExpense);
            if (newExpense != null) {
                expenseDtos.add(newExpense);
                this.recurringExpenseService.update(recurringExpense);
            }
        }
        return expenseDtos;
    }

    public ExpenseDto GenerateExpenseForDate(RecurringExpenseDto recurringExpense) {
        Calendar currentTime = this.dateProvider.getDateNow();
        if (recurringExpense.lastExpenseDate == null) {
            if (currentTime.getTime().after(recurringExpense.startDate)
                    || currentTime.getTime().equals(recurringExpense.startDate)) {
                recurringExpense.lastExpenseDate = currentTime.getTime();
                return new ExpenseDto(
                        null,
                        recurringExpense.clientId,
                        recurringExpense.categoryId,
                        recurringExpense.paymentTypeId,
                        recurringExpense.name,
                        currentTime.getTime(),
                        recurringExpense.amount,
                        recurringExpense.recurringExpenseId
                );
            }
        } else {
            Calendar lastExpense = new GregorianCalendar();
            lastExpense.setTime(recurringExpense.lastExpenseDate);
            lastExpense.set(Calendar.MINUTE, 0);
            lastExpense.set(Calendar.HOUR, 0);
            lastExpense.set(Calendar.SECOND, 0);
            lastExpense.set(Calendar.MILLISECOND, 0);
            Calendar dateBeforeChange = (Calendar) lastExpense.clone();
            switch (recurringExpense.frequency) {
                case MONTHLY:
                    lastExpense.add(Calendar.MONTH, 1);
                    break;
                case WEEKLY:
                    lastExpense.add(Calendar.HOUR, 7 * 24);
                    break;
                case DAILY:
                    lastExpense.add(Calendar.HOUR, 24);
                    break;
                case YEARLY:
                    lastExpense.add(Calendar.YEAR, 1);
                    break;
                default:
                    break;
            }
            if (dateBeforeChange.compareTo(lastExpense) != 0) {
                if (lastExpense.compareTo(currentTime) <= 0) {
                    recurringExpense.lastExpenseDate = lastExpense.getTime();
                    return new ExpenseDto(
                            null,
                            recurringExpense.clientId,
                            recurringExpense.categoryId,
                            recurringExpense.paymentTypeId,
                            recurringExpense.name,
                            currentTime.getTime(),
                            recurringExpense.amount,
                            recurringExpense.recurringExpenseId
                    );
                }
            }
        }
        return null;
    }
}
