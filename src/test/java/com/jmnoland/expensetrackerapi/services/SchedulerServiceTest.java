package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.enums.Frequency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTest {

    private SchedulerService classUnderTest;
    @Mock
    private RecurringExpenseServiceInterface recurringExpenseService;
    @Mock
    private DateProviderInterface dateProvider;

    @Before
    public void Setup() {
        this.classUnderTest = new SchedulerService(recurringExpenseService, null, null, dateProvider);
    }

    @Test
    public void GenerateExpense_ShouldCreateExpense_WhenLastExpenseDateIsNull() {
//        RecurringExpenseDto existing = new RecurringExpenseDto(null,
//                null,
//                "Test",
//                "",
//                "Subscription",
//                Calendar.getInstance(),
//                Calendar.getInstance(),
//                Frequency.MONTHLY,
//                10F,
//                null);
    }
}
