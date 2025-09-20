package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateClientRequest {
    @JsonProperty("Application")
    public String application;
    @JsonProperty("RequestId")
    public String requestId;
    @JsonProperty("ClientId")
    public String clientId;
    @JsonProperty("ClientName")
    public String clientName;
}
