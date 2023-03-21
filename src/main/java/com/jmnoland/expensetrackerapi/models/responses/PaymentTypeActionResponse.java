package com.jmnoland.expensetrackerapi.models.responses;

import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.Date;
import java.util.List;

public class PaymentTypeActionResponse {
    public String paymentTypeId;
    public String clientId;
    public boolean active;
    public String name;
    public Float charge;
    public Date archivedAt;

    public List<ValidationError> validationErrors;
}
