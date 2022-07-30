package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.UserServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SchedulerService {

    private final UserServiceInterface userService;
    private final RecurringExpenseServiceInterface recurringExpenseService;
    private final ExpenseServiceInterface expenseService;
    private final DateProviderInterface dateProvider;

    public SchedulerService(RecurringExpenseServiceInterface recurringExpenseService,
                            UserServiceInterface userService,
                            ExpenseServiceInterface expenseService,
                            DateProviderInterface dateProvider) {
        this.recurringExpenseService = recurringExpenseService;
        this.expenseService = expenseService;
        this.userService = userService;
        this.dateProvider = dateProvider;
    }
    @Scheduled(cron = "0 12 * * * *")
    public void scheduleTask()
    {
        Calendar currentTime = this.dateProvider.getDateNow();
        List<UserDto> userList = this.userService.getUsers();
        List<ExpenseDto> expenseDtos = new ArrayList<>();
        for(UserDto user : userList) {
            List<RecurringExpenseDto> recurExpenseList = this.recurringExpenseService.getRecurringExpenses(user.userId);
            for(RecurringExpenseDto recurringExpense : recurExpenseList) {
                if (recurringExpense.lastExpenseDate == null) {
                    if (currentTime.getTime().after(recurringExpense.startDate) || currentTime.getTime().equals(recurringExpense.startDate)) {
                        expenseDtos.add(new ExpenseDto(
                                null,
                                recurringExpense.userId,
                                recurringExpense.categoryId,
                                recurringExpense.paymentTypeId,
                                recurringExpense.name,
                                currentTime.getTime(),
                                recurringExpense.amount,
                                recurringExpense.recurringExpenseId
                        ));
                        recurringExpense.lastExpenseDate = currentTime.getTime();
                        this.recurringExpenseService.update(recurringExpense);
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
                            expenseDtos.add(new ExpenseDto(
                                    null,
                                    recurringExpense.userId,
                                    recurringExpense.categoryId,
                                    recurringExpense.paymentTypeId,
                                    recurringExpense.name,
                                    currentTime.getTime(),
                                    recurringExpense.amount,
                                    recurringExpense.recurringExpenseId
                            ));
                            recurringExpense.lastExpenseDate = lastExpense.getTime();
                            this.recurringExpenseService.update(recurringExpense);
                        }
                    }
                }
            }
        }
        for(ExpenseDto expenseDto : expenseDtos) {
            this.expenseService.insert(expenseDto);
        }
    }
}
