package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class ApiKeyDto {
    private String keyId;
    private String clientId;
    private String keyHash;
    private boolean active;
    private Date createdAt;
    private Date revokedAt;

    public ApiKeyDto(String keyId, String clientId, String keyHash, boolean active, Date createdAt, Date revokedAt) {
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

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getKeyHash() {
        return keyHash;
    }

    public void setKeyHash(String keyHash) {
        this.keyHash = keyHash;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getRevokedAt() {
        return revokedAt;
    }

    public void setRevokedAt(Date revokedAt) {
        this.revokedAt = revokedAt;
    }
}
