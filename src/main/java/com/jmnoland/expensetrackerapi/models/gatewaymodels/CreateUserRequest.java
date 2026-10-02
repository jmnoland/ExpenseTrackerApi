package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateUserRequest {
    @JsonProperty("Application")
    public List<ApplicationRequest> application;
    @JsonProperty("RequestId")
    public String requestId;
    @JsonProperty("UserId")
    public String userId;
    @JsonProperty("UserName")
    public String userName;
    @JsonProperty("ClientId")
    public String clientId;
}
