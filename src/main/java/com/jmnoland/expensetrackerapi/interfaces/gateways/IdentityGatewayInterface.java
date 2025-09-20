package com.jmnoland.expensetrackerapi.interfaces.gateways;

import com.jmnoland.expensetrackerapi.models.gatewaymodels.AuthenticateKeyResponse;
import com.jmnoland.expensetrackerapi.models.gatewaymodels.CreateClientRequest;
import com.jmnoland.expensetrackerapi.models.gatewaymodels.CreateClientResponse;
import com.jmnoland.expensetrackerapi.models.gatewaymodels.CreateUserCredentialRequest;

import java.io.IOException;

public interface IdentityGatewayInterface {
    AuthenticateKeyResponse createUserCredential(CreateUserCredentialRequest createRequest) throws IOException, InterruptedException;
    CreateClientResponse createClient(CreateClientRequest createRequest) throws IOException, InterruptedException;
    AuthenticateKeyResponse validateApiKey(String apiKeyHeader, String clientId) throws IOException, InterruptedException;
}
