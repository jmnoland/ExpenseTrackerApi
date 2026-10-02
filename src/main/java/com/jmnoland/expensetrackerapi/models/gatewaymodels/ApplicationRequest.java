package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApplicationRequest {
    @JsonProperty("ID")
    public String id;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Permissions")
    public List<String> permissions;
}
