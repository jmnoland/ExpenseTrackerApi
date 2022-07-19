package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("paymenttypes")
public class PaymentType {
    @Id
    private final String paymentTypeId;
    private String userId;
    private boolean active;
    private String name;
    private Float charge;
    private Date archivedAt;

    public String getPaymentTypeId() {
        return paymentTypeId;
    }
    public String getUserId() {
        return userId;
    }
    public boolean isActive() {
        return active;
    }
    public String getName() {
        return name;
    }
    public Float getCharge() {
        return charge;
    }
    public Date getArchivedAt() { return archivedAt; }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCharge(Float charge) {
        this.charge = charge;
    }
    public void setArchivedAt(Date archivedAt) { this.archivedAt = archivedAt; }

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
