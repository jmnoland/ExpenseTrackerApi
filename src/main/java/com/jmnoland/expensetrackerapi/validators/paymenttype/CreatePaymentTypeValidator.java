package com.jmnoland.expensetrackerapi.validators.paymenttype;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class CreatePaymentTypeValidator {

    public List<ValidationError> validate(PaymentTypeDto paymentTypeDto, boolean paymentTypeNameExists, boolean userExists) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (paymentTypeDto.name == null || paymentTypeDto.name.equals("")) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Name is required"
            ));
        } else if (paymentTypeNameExists) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "This name is already in use"
            ));
        }

        if (paymentTypeDto.charge == null) {
            validationErrors.add(new ValidationError(
                    "Charge",
                    "Charge cannot be null"
            ));
        } else if (paymentTypeDto.charge < 0) {
            validationErrors.add(new ValidationError(
                    "Charge",
                    "Charge cannot be less than zero"
            ));
        }

        if (!userExists) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "The user does not exist"
            ));
        }

        return validationErrors;
    }
}
