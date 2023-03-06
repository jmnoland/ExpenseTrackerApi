package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.enums.Frequency;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTest {

    private SchedulerService classUnderTest;
    @Mock
    private RecurringExpenseServiceInterface recurringExpenseService;
    @Mock
    private DateProviderInterface dateProvider;
    @Mock
    private ExpenseServiceInterface expenseService;
    @Mock
    private ApiKeyRepositoryInterface apiKeyRepository;

    @Before
    public void Setup() {
        this.classUnderTest = new SchedulerService(recurringExpenseService,
                expenseService,
                apiKeyRepository,
                dateProvider);
    }

    @Test
    public void GenerateExpense_ShouldCreateExpense_WhenLastExpenseDateIsNull() {
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(),
                Frequency.MONTHLY,
                10F,
                null);

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertTrue(response.successful);
        assertNotNull(response.responseObject);
        assertSame(response.responseObject.recurringExpenseId, existing.recurringExpenseId);
        assertTrue(response.validationErrors == null || response.validationErrors.isEmpty());
    }
}
