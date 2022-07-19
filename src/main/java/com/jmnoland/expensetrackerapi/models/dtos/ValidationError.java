package com.jmnoland.expensetrackerapi.models.dtos;

public class ValidationError {

    public String affectedField;

    public String message;

    public ValidationError(String affectedField, String message) {
        this.affectedField = affectedField;
        this.message = message;
    }
}
