package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.CategoryRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.LineItemRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.ExpenseMapper;
import com.jmnoland.expensetrackerapi.mapping.LineItemMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.Expense;

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
public class ExpenseServiceTest {

    private ExpenseService classUnderTest;
    @Mock
    private ExpenseRepositoryInterface expenseRepository;
    @Mock
    private CategoryRepositoryInterface categoryRepository;
    @Mock
    private PaymentTypeRepositoryInterface paymentTypeRepository;
    @Mock
    private LineItemRepositoryInterface lineItemRepository;
    @Mock
    private DateProviderInterface dateProvider;
    @Mock
    private ExpenseMapper expenseMapper;
    @Mock
    private LineItemMapper lineItemMapper;

    @Before
    public void Setup() {
        classUnderTest = new ExpenseService(expenseRepository,
                categoryRepository,
                paymentTypeRepository,
                lineItemRepository,
                expenseMapper,
                lineItemMapper,
                dateProvider);
    }

    // insert tests
    @Test
    public void InsertExpense_ShouldBeSuccessful_WhenValidationSucceeds() {
        ExpenseDto request = new ExpenseDto("0",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                10F,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepository.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepository.paymentTypeExistsId("1")).thenReturn(true);
        when(expenseMapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(expenseRepository, times(1)).insert(output);
    }
    @Test
    public void InsertExpense_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        ExpenseDto request = new ExpenseDto("1",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepository.categoryExists(null)).thenReturn(false);
        when(paymentTypeRepository.paymentTypeExistsId(null)).thenReturn(false);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(8, response.validationErrors.size());
        verify(expenseRepository, never()).insert(output);
    }
    @Test
    public void InsertExpense_ShouldNotBeSuccessful_WhenEntitiesDoNotExist() {
        ExpenseDto request = new ExpenseDto("0",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                10F,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(categoryRepository.categoryExists("1")).thenReturn(false);
        when(paymentTypeRepository.paymentTypeExistsId("1")).thenReturn(false);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(2, response.validationErrors.size());
        verify(expenseRepository, never()).insert(output);
    }

    // update tests
    @Test
    public void UpdateExpense_ShouldBeSuccessful_WhenValidationSucceeds() {
        ExpenseDto request = new ExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                10F,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(expenseRepository.expenseExists("1")).thenReturn(true);
        when(categoryRepository.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepository.paymentTypeExistsId("1")).thenReturn(true);
        when(expenseMapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(expenseRepository, times(1)).update(output);
    }
    @Test
    public void UpdateExpense_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        ExpenseDto request = new ExpenseDto("1",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(expenseRepository.expenseExists("1")).thenReturn(true);
        when(categoryRepository.categoryExists(null)).thenReturn(false);
        when(paymentTypeRepository.paymentTypeExistsId(null)).thenReturn(false);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(8, response.validationErrors.size());
        verify(expenseRepository, never()).update(output);
    }
    @Test
    public void UpdateExpense_ShouldNotBeSuccessful_WhenExpenseDoesNotExist() {
        ExpenseDto request = new ExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                10F,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(expenseRepository.expenseExists("1")).thenReturn(false);
        when(categoryRepository.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepository.paymentTypeExistsId("1")).thenReturn(true);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        assertEquals("ExpenseId", response.validationErrors.get(0).affectedField);
        verify(expenseRepository, never()).update(output);
    }
    @Test
    public void UpdateExpense_ShouldBeSuccessful_WhenExpenseExists() {
        ExpenseDto request = new ExpenseDto("1",
                "1",
                "1",
                "1",
                "TestName",
                new Date(1),
                10F,
                null);
        Expense output = ExpenseMapper.INSTANCE.dtoToEntity(request);
        when(expenseRepository.expenseExists("1")).thenReturn(true);
        when(categoryRepository.categoryExists("1")).thenReturn(true);
        when(paymentTypeRepository.paymentTypeExistsId("1")).thenReturn(true);
        when(expenseMapper.dtoToEntity(request)).thenReturn(output);

        ServiceResponse<ExpenseDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        verify(expenseRepository, times(1)).update(output);
    }
}
