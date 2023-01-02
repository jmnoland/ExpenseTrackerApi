package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.responses.ApiKeyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final AuthenticationServiceInterface authenticationService;
    @Autowired
    public ClientController(AuthenticationServiceInterface authenticationServiceInterface) {
        this.authenticationService = authenticationServiceInterface;
    }

    @PostMapping()
    public ServiceResponse<ApiKeyResponse> createClientApiKey() {
        return this.authenticationService.createClientApiKey();
    }
}
