package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.CategoryDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.Category;

import com.jmnoland.expensetrackerapi.models.responses.CategoryActionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto entityToDto(Category obj);
    List<CategoryDto> entityToDto(List<Category> list);

    Category dtoToEntity(CategoryDto obj);
    List<Category> dtoToEntity(List<CategoryDto> list);

    CategoryActionResponse dtoToResponse(CategoryDto obj, List<ValidationError> validationErrors);
}
