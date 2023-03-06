package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.mapping.PaymentTypeMapper;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;

import com.jmnoland.expensetrackerapi.models.requests.CreateUpdatePaymentTypeRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

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
    private PaymentTypeMapper mapper;
    @Mock
    private DateProviderInterface dateProviderInterface;

    @Before
    public void Setup() {
        this.classUnderTest = new PaymentTypeService(paymentTypeRepositoryInterface, mapper, dateProviderInterface);
    }

    // insert tests
    @Test
    public void InsertPaymentType_ShouldBeSuccessful_WhenPaymentTypeIsValid() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "test";
        request.paymentTypeId = "1";
        request.archivePaymentType = false;
        request.charge = 0f;

        PaymentType output = new PaymentType(
                request.paymentTypeId,
                request.clientId,
                true,
                request.name,
                request.charge,
                null);

        when(this.mapper.dtoToEntity(Mockito.any(PaymentTypeDto.class))).thenReturn(output);
        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertTrue(response.validationErrors.isEmpty());
        verify(paymentTypeRepositoryInterface, times(1)).insert(output);
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = null;
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = null;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(2, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(Mockito.any());
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenPaymentTypeNameAlreadyExists() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = false;
        request.charge = 1F;

        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(true);
        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(Mockito.any());
    }
    @Test
    public void InsertPaymentType_ShouldBeSuccessful_WhenPaymentTypeNameExistsWithArchiving() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = 1F;

        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(true);
        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertTrue(response.validationErrors.isEmpty());
        verify(paymentTypeRepositoryInterface, times(1)).insert(Mockito.any());
    }
    @Test
    public void InsertPaymentType_ShouldNotBeSuccessful_WhenChargeIsLessThanZero() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = -1F;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());
        when(paymentTypeRepositoryInterface.paymentTypeExists("Card")).thenReturn(false);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.insert(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).insert(Mockito.any());
    }

    // update tests
    @Test
    public void UpdatePaymentType_ShouldBeSuccessful_WhenPaymentTypeIsValid() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = 1F;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());
        when(paymentTypeRepositoryInterface.paymentTypeExistsId("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertTrue(response.successful);
        assertNotNull(response.validationErrors);
        assertTrue(response.validationErrors.isEmpty());
        verify(paymentTypeRepositoryInterface, times(1)).update(Mockito.any());
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenRequiredFieldsAreNULL() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = null;
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = null;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());
        when(paymentTypeRepositoryInterface.paymentTypeExistsId("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(2, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(Mockito.any());
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenPaymentTypeDoesNotExist() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = 1F;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());
        when(paymentTypeRepositoryInterface.paymentTypeExistsId("1")).thenReturn(false);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(Mockito.any());
    }
    @Test
    public void UpdatePaymentType_ShouldNotBeSuccessful_WhenChargeIsLessThanZero() {
        CreateUpdatePaymentTypeRequest request = new CreateUpdatePaymentTypeRequest();
        request.clientId = "1";
        request.name = "Card";
        request.paymentTypeId = "1";
        request.archivePaymentType = true;
        request.charge = -1F;

        when(dateProviderInterface.getDateNow()).thenReturn(Calendar.getInstance());
        when(paymentTypeRepositoryInterface.paymentTypeExistsId("1")).thenReturn(true);

        ServiceResponse<PaymentTypeDto> response = this.classUnderTest.update(request);

        assertFalse(response.successful);
        assertNotNull(response.validationErrors);
        assertEquals(1, response.validationErrors.size());
        verify(paymentTypeRepositoryInterface, never()).update(Mockito.any());
    }
}
