package com.jmnoland.expensetrackerapi.models.dtos;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ServiceResponse<T> {

    public T responseObject;
    public boolean successful;
    @NotNull
    public List<ValidationError> validationErrors;

    public ServiceResponse(
        T responseObject,
        boolean successful,
        @NotNull List<ValidationError> validationErrors
    ) {
        this.responseObject = responseObject;
        this.successful = successful;
        this.validationErrors = validationErrors;
    }

    public ServiceResponse(
        T responseObject,
        boolean successful
    ) {
        this.responseObject =responseObject;
        this.successful = successful;
        this.validationErrors = new ArrayList<>();
    }
}
