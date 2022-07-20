package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;

import java.util.List;

public interface UserServiceInterface {

    List<UserDto> getUsers();

    UserDto getUser(String userId);

    boolean usersExist();

    ServiceResponse<UserDto> insert(UserDto userDto);

    void delete(UserDto userDto);

    ServiceResponse<UserDto> update(UserDto userDto);
}
