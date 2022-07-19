package com.jmnoland.expensetrackerapi.models.dtos;

import java.util.List;

public class ServiceResponse<T> {

    public T responseObject;

    public boolean successful;

    public List<ValidationError> validationErrors;

    public ServiceResponse(
        T responseObject,
        boolean successful,
        List<ValidationError> validationErrors
    ) {
        this.responseObject = responseObject;
        this.successful = successful;
        this.validationErrors = validationErrors;
    }
}
