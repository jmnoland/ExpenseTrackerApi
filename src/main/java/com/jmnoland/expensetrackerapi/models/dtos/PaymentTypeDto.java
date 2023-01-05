package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class PaymentTypeDto {

    public String paymentTypeId;
    public String clientId;
    public boolean active;
    public String name;
    public Float charge;
    public Date archivedAt;

    public PaymentTypeDto(
            String paymentTypeId,
            String clientId,
            boolean active,
            String name,
            Float charge,
            Date archivedAt
    ) {
        this.paymentTypeId = paymentTypeId;
        this.clientId = clientId;
        this.active = active;
        this.name = name;
        this.charge = charge;
        this.archivedAt = archivedAt;
    }
}
