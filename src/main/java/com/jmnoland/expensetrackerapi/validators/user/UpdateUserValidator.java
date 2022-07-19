package com.jmnoland.expensetrackerapi.validators.user;

import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateUserValidator {

    public List<ValidationError> validate(UserDto userDto, List<User> userList) {
        List<ValidationError> validationErrors = new ArrayList<>();
        boolean userExists = false;
        if (userDto.password == null || userDto.password.equals("")) {
            validationErrors.add(new ValidationError(
                    "Password",
                    "User password is required"
            ));
        }
        for(User existingUser : userList) {
            boolean isExistingUser = Objects.equals(existingUser.getUserId(), userDto.userId);
            if (isExistingUser) userExists = true;
            if (Objects.equals(existingUser.getEmail(), userDto.email) && !isExistingUser) {
                validationErrors.add(new ValidationError(
                        "Email",
                        "A user with this email already exists"
                ));
            }
            if (Objects.equals(existingUser.getName(), userDto.name) && !isExistingUser) {
                validationErrors.add(new ValidationError(
                        "Name",
                        "A user with this name already exists"
                ));
            }
        }
        if (!userExists) {
            validationErrors.add(new ValidationError(
                    "UserId",
                    "This user does not exist"
            ));
        }
        return validationErrors;
    }
}
