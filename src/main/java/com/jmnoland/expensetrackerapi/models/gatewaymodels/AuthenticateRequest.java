package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticateRequest {
    @JsonProperty("Application")
    public String application;
    @JsonProperty("RequestId")
    public String requestId;
    @JsonProperty("Identifier")
    public String identifier;
    @JsonProperty("Secret")
    public String secret;
    @JsonProperty("ClientId")
    public String clientId;
    @JsonProperty("ClientName")
    public String clientName;
}
