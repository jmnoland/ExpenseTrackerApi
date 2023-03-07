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
    @Test
    public void GenerateExpense_ShouldCreateMonthlyExpense_WhenLastExpenseAtLeastMonthBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.MONTH, -1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.MONTHLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertTrue(response.successful);
        assertNotNull(response.responseObject);
        assertSame(response.responseObject.recurringExpenseId, existing.recurringExpenseId);
        assertTrue(response.validationErrors == null || response.validationErrors.isEmpty());
    }
    @Test
    public void GenerateExpense_ShouldNotCreateMonthlyExpense_WhenLastExpenseLargerThanMonthBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.MONTH, -1);
        lastExpenseDate.add(Calendar.HOUR, 24);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.MONTHLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertFalse(response.successful);
        assertNull(response.responseObject);
    }

    @Test
    public void GenerateExpense_ShouldCreateWeeklyExpense_WhenLastExpenseAtLeastWeekBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR, (7 * 24) * -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.HOUR, (7 * 24) * -1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.WEEKLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertTrue(response.successful);
        assertNotNull(response.responseObject);
        assertSame(response.responseObject.recurringExpenseId, existing.recurringExpenseId);
        assertTrue(response.validationErrors == null || response.validationErrors.isEmpty());
    }
    @Test
    public void GenerateExpense_ShouldNotCreateWeeklyExpense_WhenLastExpenseLargerThanWeekBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR, (7 * 24) * -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.HOUR, (6 * 24) * -1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.WEEKLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertFalse(response.successful);
        assertNull(response.responseObject);
    }

    @Test
    public void GenerateExpense_ShouldCreateDailyExpense_WhenLastExpenseAtLeastDayBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.HOUR, 24 * -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.HOUR, 24 * -1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.DAILY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertTrue(response.successful);
        assertNotNull(response.responseObject);
        assertSame(response.responseObject.recurringExpenseId, existing.recurringExpenseId);
        assertTrue(response.validationErrors == null || response.validationErrors.isEmpty());
    }

    @Test
    public void GenerateExpense_ShouldCreateYearlyExpense_WhenLastExpenseAtLeastYearBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.YEAR, -1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.YEARLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertTrue(response.successful);
        assertNotNull(response.responseObject);
        assertSame(response.responseObject.recurringExpenseId, existing.recurringExpenseId);
        assertTrue(response.validationErrors == null || response.validationErrors.isEmpty());
    }
    @Test
    public void GenerateExpense_ShouldNotCreateYearlyExpense_WhenLastExpenseLargerThanYearBefore() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -2);
        Calendar lastExpenseDate = Calendar.getInstance();
        lastExpenseDate.add(Calendar.YEAR, -1);
        lastExpenseDate.add(Calendar.MONTH, 1);
        RecurringExpenseDto existing = new RecurringExpenseDto(UUID.randomUUID().toString(),
                "client1",
                "Test",
                "typeId",
                "Subscription",
                startDate.getTime(),
                null,
                Frequency.YEARLY,
                10F,
                lastExpenseDate.getTime());

        ServiceResponse<CreateUpdateExpenseRequest> response = this.classUnderTest.GenerateExpenseForDate(existing, Calendar.getInstance());

        assertFalse(response.successful);
        assertNull(response.responseObject);
    }
}
