package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.UserDto;
import com.jmnoland.expensetrackerapi.models.entities.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(User user);
    List<UserDto> entityToDto(List<User> list);

    User dtoToEntity(UserDto userDto);
    List<User> dtoToEntity(List<UserDto> list);
}
