package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdatePaymentTypeRequest;

import java.util.List;

public interface PaymentTypeServiceInterface {

    ServiceResponse<List<PaymentTypeDto>> getPaymentTypes(String clientId);

    ServiceResponse<PaymentTypeDto> insert(CreateUpdatePaymentTypeRequest payload);

    ServiceResponse<String> archivePaymentType(String paymentTypeId, String clientId);

    void delete(PaymentTypeDto paymentType);

    ServiceResponse<PaymentTypeDto> update(CreateUpdatePaymentTypeRequest payload);
}
