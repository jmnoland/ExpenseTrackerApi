package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserCredentialRequest {
    @JsonProperty("User")
    public CreateUserRequest user;
    @JsonProperty("Credential")
    public CreateCredentialRequest credential;
}
