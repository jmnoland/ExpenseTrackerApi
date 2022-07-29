package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.PaymentTypeMapper;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentTypeServiceTest {

    private PaymentTypeService classUnderTest;
    @Mock
    private PaymentTypeRepositoryInterface paymentTypeRepositoryInterface;
    @Mock
    private UserRepositoryInterface userRepositoryInterface;
    @Mock
    private PaymentTypeMapper mapper;

    @Before
    public void Setup() {
        this.classUnderTest = new PaymentTypeService(paymentTypeRepositoryInterface, userRepositoryInterface, mapper);
    }

    // insert tests
    @Test
    public void InsertPaymentType_ShouldBeSuccessful_WhenPaymentTypeIsValid() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(this.mapper.dtoToEntity(request)).thenReturn(output);
        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(false);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertTrue(response.validationErrors.isEmpty());
        verify(paymentTypeRepositoryInterface, times(1)).insert(output);
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, null, null, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(2, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(output);
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenPaymentTypeNameAlreadyExists() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(true);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(output);
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenChargeIsLessThanZero() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", -1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(false);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(output);
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenUserDoesNotExist() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(false);
        when(userRepositoryInterface.userExists("1")).thenReturn(false);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(output);
    }

    // update tests
    @Test
    public void UpdatePaymentType_ShouldBeSuccessful_WhenPaymentTypeIsValid() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(this.mapper.dtoToEntity(request)).thenReturn(output);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertTrue(response.validationErrors.isEmpty());
        verify(paymentTypeRepositoryInterface, times(1)).update(output);
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, null, null, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(2, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(output);
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenPaymentTypeDoesNotExist() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(false);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(output);
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenUserDoesNotExist() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", 1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(userRepositoryInterface.userExists("1")).thenReturn(false);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(output);
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenChargeIsLessThanZero() {
        PaymentTypeDto request = new PaymentTypeDto("1", "1", true, "Card", -1F, null);
        PaymentType output = this.mapper.dtoToEntity(request);
        when(paymentTypeRepositoryInterface.paymentTypeExists("1")).thenReturn(true);
        when(userRepositoryInterface.userExists("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(output);
    }
}
