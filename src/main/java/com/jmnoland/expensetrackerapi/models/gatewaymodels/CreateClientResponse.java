package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CreateClientResponse {
    @JsonProperty("ID")
    public String id;
    @JsonProperty("Application")
    public String application;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("CreatedAt")
    public Date createdAt;
    @JsonProperty("ModifiedAt")
    public Date modifiedAt;
}
