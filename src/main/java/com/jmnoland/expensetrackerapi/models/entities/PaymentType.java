package com.jmnoland.expensetrackerapi.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document("paymenttypes")
public class PaymentType {
    @Id
    private final String paymentTypeId;
    private String userId;
    private boolean active;
    private String name;
    private Float charge;
    private Date archivedAt;

    public PaymentType(
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
