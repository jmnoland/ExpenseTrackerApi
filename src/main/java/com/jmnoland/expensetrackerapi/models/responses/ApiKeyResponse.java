package com.jmnoland.expensetrackerapi.models.responses;

public class ApiKeyResponse {
    public String Id;
    public String keySecret;

    public String ClientId;

    public ApiKeyResponse(String id, String keySecret, String clientId) {
        this.Id = id;
        this.keySecret = keySecret;
        this.ClientId = clientId;
    }
}
