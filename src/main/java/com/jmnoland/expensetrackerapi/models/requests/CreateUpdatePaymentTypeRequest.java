package com.jmnoland.expensetrackerapi.models.requests;

import com.mongodb.lang.Nullable;

public class CreateUpdatePaymentTypeRequest {
    @Nullable
    public String paymentTypeId;
    @Nullable
    public String clientId;
    public String name;
    public Float charge;
    @Nullable
    public boolean archivePaymentType;
    public CreateUpdatePaymentTypeRequest() {};
}
