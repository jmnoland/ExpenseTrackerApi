package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.RecurringExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.RecurringExpenseMapper;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;
import com.jmnoland.expensetrackerapi.models.enums.Frequency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecurringExpenseServiceTest {

    private RecurringExpenseService classUnderTest;
    @Mock
    private RecurringExpenseRepositoryInterface recurringExpenseRepository;
    @Mock
    private CategoryRepositoryInterface categoryRepositoryInterface;
    @Mock
    private PaymentTypeRepositoryInterface paymentTypeRepositoryInterface;
    @Mock
    private RecurringExpenseMapper mapper;

    @Before
    public void Setup() {
        classUnderTest = new RecurringExpenseService(
                recurringExpenseRepository,
                categoryRepositoryInterface,
                paymentTypeRepositoryInterface,
                mapper);
    }

    // insert tests
    @Test
    public void InsertRecurringExpense_ShouldBeSuccessful_WhenValidationSucceeds() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(mapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(recurringExpenseRepository, times(1)).insert(output);
    }
    @Test
    public void InsertRecurringExpense_ShouldBeSuccessful_WhenEndDateIsNULL() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                null,
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(mapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(recurringExpenseRepository, times(1)).insert(output);
    }
    @Test
    public void InsertRecurringExpense_ShouldNotBeSuccessful_WhenStartDateAfterEndDate() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                "1",
                "1",
                "1",
                "TestName",
                new Date(2),
                new Date(1),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        assertEquals("EndDate", response.validationErrors.get(0).affectedField);
        verify(recurringExpenseRepository, never()).insert(output);
    }
    @Test
    public void InsertRecurringExpense_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists(null)).thenReturn(false);
        when(paymentTypeRepositoryInterface.paymentTypeExists(null)).thenReturn(false);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(6, response.validationErrors.size());
        verify(recurringExpenseRepository, never()).insert(output);
    }
    @Test
    public void InsertRecurringExpense_ShouldNotBeSuccessful_WhenEntitiesDoNotExist() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(false);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(false);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(3, response.validationErrors.size());
        verify(recurringExpenseRepository, never()).insert(output);
    }

    // update tests
    @Test
    public void UpdateRecurringExpense_ShouldBeSuccessful_WhenValidationSucceeds() {
        RecurringExpenseDto request = new RecurringExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(recurringExpenseRepository.recurringExpenseExists("1")).thenReturn(true);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(mapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(recurringExpenseRepository, times(1)).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldBeSuccessful_WhenEndDateIsNULL() {
        RecurringExpenseDto request = new RecurringExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                null,
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(recurringExpenseRepository.recurringExpenseExists("1")).thenReturn(true);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(mapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(recurringExpenseRepository, times(1)).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldNotBeSuccessful_WhenStartDateAfterEndDate() {
        RecurringExpenseDto request = new RecurringExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(2),
                new Date(1),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(recurringExpenseRepository.recurringExpenseExists("1")).thenReturn(true);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        assertEquals("EndDate", response.validationErrors.get(0).affectedField);
        verify(recurringExpenseRepository, never()).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists(null)).thenReturn(false);
        when(paymentTypeRepositoryInterface.paymentTypeExists(null)).thenReturn(false);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(7, response.validationErrors.size());
        verify(recurringExpenseRepository, never()).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldNotBeSuccessful_WhenExpenseDoesNotExist() {
        RecurringExpenseDto request = new RecurringExpenseDto(null,
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        assertEquals("RecurringExpenseId", response.validationErrors.get(0).affectedField);
        verify(recurringExpenseRepository, never()).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldBeSuccessful_WhenRecurringExpenseExists() {
        RecurringExpenseDto request = new RecurringExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(recurringExpenseRepository.recurringExpenseExists("1")).thenReturn(true);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(mapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(recurringExpenseRepository, times(1)).update(output);
    }
    @Test
    public void UpdateRecurringExpense_ShouldBeSuccessful_WhenEntitiesDoNotExist() {
        RecurringExpenseDto request = new RecurringExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                new Date(2),
                Frequency.MONTHLY,
                10F,
                null);
        RecurringExpense output = RecurringExpenseMapper.INSTANCE.dtoToEntity(request);
        when(recurringExpenseRepository.recurringExpenseExists("1")).thenReturn(false);
        when(categoryRepositoryInterface.categoryExists("1")).thenReturn(false);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(false);

        ServiceResponse<RecurringExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(4, response.validationErrors.size());
        verify(recurringExpenseRepository, never()).update(output);
    }
}
