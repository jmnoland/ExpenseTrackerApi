package com.jmnoland.expensetrackerapi.controllers;

import com.jmnoland.expensetrackerapi.interfaces.services.UserServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceInterface userService;

    @Autowired
    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserDto> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping()
    public ServiceResponse<UserDto> createUser(@RequestBody UserDto userDto) {
        return this.userService.insert(userDto);
    }

    @PatchMapping()
    public ServiceResponse<UserDto> updateUser(@RequestBody UserDto userDto) {
        return this.userService.update(userDto);
    }

    @DeleteMapping()
    public void deleteUser(@RequestBody UserDto userDto) {
        this.userService.delete(userDto);
    }
}
