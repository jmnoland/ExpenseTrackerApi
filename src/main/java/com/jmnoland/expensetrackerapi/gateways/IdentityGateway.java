package com.jmnoland.expensetrackerapi.gateways;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmnoland.expensetrackerapi.interfaces.gateways.IdentityGatewayInterface;
import com.jmnoland.expensetrackerapi.models.gatewaymodels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class IdentityGateway implements IdentityGatewayInterface {

    private final HttpClient httpClient;
    private final String identityUrl;
    @Autowired
    public IdentityGateway() {
        this.httpClient = HttpClient.newHttpClient();
        this.identityUrl = "";
    }

    public CreateClientResponse createClient(CreateClientRequest createRequest) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(this.identityUrl + "/client"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(createRequest)))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = this.httpClient.send(req, handler);

        return mapper.readValue(response.body(), CreateClientResponse.class);
    }

    public AuthenticateKeyResponse createUserCredential(CreateUserCredentialRequest createRequest) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(this.identityUrl + "/user/credential"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(createRequest)))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = this.httpClient.send(req, handler);

        return mapper.readValue(response.body(), AuthenticateKeyResponse.class);
    }

    public AuthenticateKeyResponse validateApiKey(String apiKeyHeader, String clientId) throws IOException, InterruptedException {
        AuthenticateRequest authRequest = new AuthenticateRequest();
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(this.identityUrl + "/auth/key"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(authRequest)))
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = this.httpClient.send(req, handler);

        return mapper.readValue(response.body(), AuthenticateKeyResponse.class);
    }
}
