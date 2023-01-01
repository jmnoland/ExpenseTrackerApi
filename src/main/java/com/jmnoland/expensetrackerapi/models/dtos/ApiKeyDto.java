package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.Date;

public class ApiKeyDto {
    public String Id;
    public String KeyHash;
    public boolean Active;
    public Date CreatedAt;
    public Date RevokedAt;

    public ApiKeyDto(String id, String keyHash, boolean active, Date createdAt, Date revokedAt) {
        this.Id = id;
        this.KeyHash = keyHash;
        this.Active = active;
        this.CreatedAt = createdAt;
        this.RevokedAt = revokedAt;
    }
}
