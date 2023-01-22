package com.jmnoland.expensetrackerapi.models.requests;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;

import java.util.Date;

public class CreatePaymentTypeRequest extends PaymentTypeDto {
    public boolean archivePaymentType;
    public CreatePaymentTypeRequest(String paymentTypeId,
                                    String clientId,
                                    boolean active,
                                    String name,
                                    Float charge,
                                    Date archivedAt) {
        super(paymentTypeId, clientId, active, name, charge, archivedAt);
    }
}
