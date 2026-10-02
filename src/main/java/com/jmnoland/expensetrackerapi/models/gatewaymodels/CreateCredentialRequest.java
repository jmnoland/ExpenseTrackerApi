package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCredentialRequest {
    @JsonProperty("UserId")
    public String userId;
    @JsonProperty("ClientId")
    public String clientId;
    @JsonProperty("Type")
    public String type = "ApiKey";
    @JsonProperty("Application")
    public String application;
    @JsonProperty("Identifier")
    public String identifier;
    @JsonProperty("Secret")
    public String secret;
    @JsonProperty("RequestId")
    public String requestId;
}
