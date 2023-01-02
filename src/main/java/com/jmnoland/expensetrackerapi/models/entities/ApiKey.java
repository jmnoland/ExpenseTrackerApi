package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("apikeys")
public class ApiKey {

    @Id
    private final String id;
    private String keyId;
    private String clientId;
    private String keyHash;
    private boolean active;
    private Date createdAt;
    private Date revokedAt;

    public ApiKey(String id, String keyId, String clientId, String keyHash, boolean active, Date createdAt, Date revokedAt) {
        this.id = id;
        this.keyId = keyId;
        this.clientId = clientId;
        this.keyHash = keyHash;
        this.active = active;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public String getKeyId() {
        return keyId;
    }
    public void setKeyId(String keyId) { this.keyId = keyId; }

    public String getKeyHash() {
        return keyHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getRevokedAt() {
        return revokedAt;
    }

    public String getClientId() {
        return clientId;
    }
}
