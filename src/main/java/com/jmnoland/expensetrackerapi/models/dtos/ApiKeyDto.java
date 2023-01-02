package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class ApiKeyDto {
    private String Id;
    private String ClientId;
    private String KeyHash;
    private boolean Active;
    private Date CreatedAt;
    private Date RevokedAt;

    public ApiKeyDto(String id, String clientId, String keyHash, boolean active, Date createdAt, Date revokedAt) {
        this.Id = id;
        this.ClientId = clientId;
        this.KeyHash = keyHash;
        this.Active = active;
        this.CreatedAt = createdAt;
        this.RevokedAt = revokedAt;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getKeyHash() {
        return KeyHash;
    }

    public void setKeyHash(String keyHash) {
        KeyHash = keyHash;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getRevokedAt() {
        return RevokedAt;
    }

    public void setRevokedAt(Date revokedAt) {
        RevokedAt = revokedAt;
    }
}
