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
import java.util.Optional;

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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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
                null,
                true);
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

    // confirm tests
    @Test
    public void ConfirmExpense_ShouldBeSuccessful_WhenExpenseIsUnconfirmed() {
        Expense existing = new Expense("1", "1", "1", "1", "TestName", new Date(1), 10F, "2", false);
        when(expenseRepository.getExpense("1", "1")).thenReturn(Optional.of(existing));

        ServiceResponse<ExpenseDto> response = this.classUnderTest.confirm("1", "1", 25F);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(0, response.validationErrors.size());
        assertEquals(Float.valueOf(25F), existing.getAmount());
        assertTrue(existing.getConfirmed());
        verify(expenseRepository, times(1)).update(existing);
    }
    @Test
    public void ConfirmExpense_ShouldNotBeSuccessful_WhenExpenseIsAlreadyConfirmed() {
        Expense existing = new Expense("1", "1", "1", "1", "TestName", new Date(1), 10F, "2", true);
        when(expenseRepository.getExpense("1", "1")).thenReturn(Optional.of(existing));

        ServiceResponse<ExpenseDto> response = this.classUnderTest.confirm("1", "1", 25F);

        assertFalse(response.successful);
        assertEquals(1, response.validationErrors.size());
        assertEquals("ExpenseId", response.validationErrors.get(0).affectedField);
        assertEquals(Float.valueOf(10F), existing.getAmount());
        verify(expenseRepository, never()).update(existing);
    }
    @Test
    public void ConfirmExpense_ShouldNotBeSuccessful_WhenExpenseDoesNotExist() {
        when(expenseRepository.getExpense("1", "1")).thenReturn(Optional.empty());

        ServiceResponse<ExpenseDto> response = this.classUnderTest.confirm("1", "1", 25F);

        assertFalse(response.successful);
        assertEquals(1, response.validationErrors.size());
        assertEquals("ExpenseId", response.validationErrors.get(0).affectedField);
    }
    @Test
    public void ConfirmExpense_ShouldNotBeSuccessful_WhenAmountIsNull() {
        Expense existing = new Expense("1", "1", "1", "1", "TestName", new Date(1), 10F, "2", false);
        when(expenseRepository.getExpense("1", "1")).thenReturn(Optional.of(existing));

        ServiceResponse<ExpenseDto> response = this.classUnderTest.confirm("1", "1", null);

        assertFalse(response.successful);
        assertEquals(1, response.validationErrors.size());
        assertEquals("Amount", response.validationErrors.get(0).affectedField);
        assertFalse(existing.getConfirmed());
    }
}
