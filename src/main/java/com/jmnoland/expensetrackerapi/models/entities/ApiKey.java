package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("apikeys")
public class ApiKey {

    private String Id;
    private String ClientId;
    private String KeyHash;
    private boolean Active;
    private Date CreatedAt;
    private Date RevokedAt;

    public ApiKey(String id, String clientId, String keyHash, boolean active, Date createdAt, Date revokedAt) {
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

    public String getKeyHash() {
        return KeyHash;
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

    public Date getRevokedAt() {
        return RevokedAt;
    }

    public String getClientId() {
        return ClientId;
    }
}
