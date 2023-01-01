package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.List;

public interface PaymentTypeServiceInterface {

    List<PaymentTypeDto> getPaymentTypes(String clientId);

    ServiceResponse<PaymentTypeDto> insert(PaymentTypeDto paymentType);

    void delete(PaymentTypeDto paymentType);

    ServiceResponse<PaymentTypeDto> update(PaymentTypeDto paymentType);
}
