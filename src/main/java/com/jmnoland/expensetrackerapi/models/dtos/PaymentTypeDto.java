package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class PaymentTypeDto {
    public String paymentTypeId;
    public String userId;
    public boolean active;
    public String name;
    public Float charge;
    public Date archivedAt;

    public PaymentTypeDto(
            String paymentTypeId,
            String userId,
            boolean active,
            String name,
            Float charge,
            Date archivedAt
    ) {
        this.paymentTypeId = paymentTypeId;
        this.userId = userId;
        this.active = active;
        this.name = name;
        this.charge = charge;
        this.archivedAt = archivedAt;
    }
}
