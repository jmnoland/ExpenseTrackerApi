package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.ApiKeyDto;
import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiKeyMapper {

    ApiKeyMapper INSTANCE = Mappers.getMapper(ApiKeyMapper.class);

    ApiKeyDto entityToDto(ApiKey obj);
    List<ApiKeyDto> entityToDto(List<ApiKey> list);

    ApiKey dtoToEntity(ApiKeyDto obj);
    List<ApiKey> dtoToEntity(List<ApiKeyDto> list);
}
