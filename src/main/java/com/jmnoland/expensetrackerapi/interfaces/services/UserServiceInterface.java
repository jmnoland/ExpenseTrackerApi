package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    List<UserDto> getUsers();

    Optional<UserDto> getUser(String userId);

    boolean userExist(String userId);

    ServiceResponse<UserDto> insert(UserDto userDto);

    void delete(UserDto userDto);

    ServiceResponse<UserDto> update(UserDto userDto);
}
