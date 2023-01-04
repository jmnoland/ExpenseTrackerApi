package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.helpers.ApiKeyHelper;
import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.responses.ApiKeyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final AuthenticationServiceInterface authenticationService;
    private final HttpServletRequest request;

    @Autowired
    public ClientController(AuthenticationServiceInterface authenticationServiceInterface,
                            HttpServletRequest request) {
        this.authenticationService = authenticationServiceInterface;
        this.request = request;
    }

    @PostMapping("create")
    @ResponseBody
    public ServiceResponse<ApiKeyResponse> createClientApiKey() {
        return this.authenticationService.createClientApiKey();
    }

    @PostMapping("revoke")
    @ResponseBody
    public ServiceResponse<ApiKeyResponse> revokeCreateNewApiKey() {
        String header = ApiKeyHelper.getBase64String(this.request);
        return this.authenticationService.createNewClientApiKey(header);
    }
}
