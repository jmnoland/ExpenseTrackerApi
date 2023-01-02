package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.responses.ApiKeyResponse;

public interface AuthenticationServiceInterface {
    ServiceResponse<ApiKeyResponse> createClientApiKey();
}
