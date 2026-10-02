package com.jmnoland.expensetrackerapi.models.gatewaymodels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ApplicationResponse {
    @JsonProperty("ID")
    public String id;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Permissions")
    public List<String> permissions;
    @JsonProperty("ModifiedAt")
    public Date modifiedAt;
}
