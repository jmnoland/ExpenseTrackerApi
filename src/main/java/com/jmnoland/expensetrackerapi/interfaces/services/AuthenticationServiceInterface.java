package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.UserDto;

public interface AuthenticationServiceInterface {

    String login(UserDto userDto) throws Exception;

    String refreshLogin(String sessionId) throws Exception;

    boolean isLoggedIn(String sessionId);
}
