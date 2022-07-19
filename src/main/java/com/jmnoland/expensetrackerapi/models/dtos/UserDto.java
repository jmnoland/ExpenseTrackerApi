package com.jmnoland.expensetrackerapi.models.dtos;

public class UserDto {
    public String userId;
    public String name;
    public String email;
    public String password;

    public UserDto(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
