package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class AuthenticateKeyResponse {
    @JsonProperty("ID")
    public String id;
    @JsonProperty("ClientId")
    public String clientId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("CreatedAt")
    public Date createdAt;
    @JsonProperty("ModifiedAt")
    public Date modifiedAt;
    @JsonProperty("Applications")
    public List<ApplicationResponse> applications;
}
