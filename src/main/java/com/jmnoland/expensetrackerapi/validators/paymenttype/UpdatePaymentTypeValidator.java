package com.jmnoland.expensetrackerapi.validators.paymenttype;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class UpdatePaymentTypeValidator {

    public List<ValidationError> validate(PaymentTypeDto paymentTypeDto, boolean paymentTypeExists) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (paymentTypeDto.name == null || paymentTypeDto.name.equals("")) {
            validationErrors.add(new ValidationError(
                    "Name",
                    "Name is required"
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

        if (!paymentTypeExists) {
            validationErrors.add(new ValidationError(
                    "PaymentTypeId",
                    "This payment type does not exist"
            ));
        }

        return validationErrors;
    }
}
