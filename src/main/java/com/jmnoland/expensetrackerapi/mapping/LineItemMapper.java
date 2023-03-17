package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.LineItemDto;
import com.jmnoland.expensetrackerapi.models.entities.LineItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LineItemMapper {
    LineItemMapper INSTANCE = Mappers.getMapper(LineItemMapper.class);
    LineItemDto entityToDto(LineItem obj);
    List<LineItemDto> entityToDto(List<LineItem> list);

    LineItem dtoToEntity(LineItemDto obj);
    List<LineItem> dtoToEntity(List<LineItemDto> list);
}
