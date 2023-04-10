package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("paymenttypes")
public class PaymentType {
    @Id
    private final String paymentTypeId;
    private String clientId;
    private boolean active;
    private String name;
    private Float charge;
    private Date archivedAt;

    public PaymentType(
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

    public String getClientId() {
        return clientId;
    }

    public String getPaymentTypeId() { return paymentTypeId; }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCharge() {
        return charge;
    }

    public void setCharge(Float charge) {
        this.charge = charge;
    }

    public Date getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Date archivedAt) {
        this.archivedAt = archivedAt;
    }
}
