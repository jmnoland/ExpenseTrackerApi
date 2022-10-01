package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("sessions")
public class Session {

    @Id
    private final String sessionId;
    private String userId;
    private Date expiryDate;
    private Date createdAt;

    public Session(String sessionId, String userId, Date expiryDate, Date createdAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.createdAt = createdAt;
    }

    public String getSessionId() { return sessionId; }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(@NotNull Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
