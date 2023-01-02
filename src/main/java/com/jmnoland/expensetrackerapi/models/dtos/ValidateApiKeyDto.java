package com.jmnoland.expensetrackerapi.models.dtos;

public class ValidateApiKeyDto {

    public String KeyId;
    public String Secret;

    public ValidateApiKeyDto(String keyId, String secret) {
        this.KeyId = keyId;
        this.Secret = secret;
    }
}
