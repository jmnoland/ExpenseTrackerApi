package com.jmnoland.expensetrackerapi.validators.user;

import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateUserValidator {

    public List<ValidationError> validate(UserDto userDto, List<User> userList) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (userDto.password == null || userDto.password.equals("")) {
            validationErrors.add(new ValidationError(
                    "Password",
                    "User password is required"
            ));
        }
        for(User existingUser : userList) {
            if (Objects.equals(existingUser.getEmail(), userDto.email)) {
                validationErrors.add(new ValidationError(
                        "Email",
                        "A user with this email already exists"
                ));
            }
            if (Objects.equals(existingUser.getName(), userDto.name)) {
                validationErrors.add(new ValidationError(
                        "Name",
                        "A user with this name already exists"
                ));
            }
        }
        return validationErrors;
    }
}
