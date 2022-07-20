package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.ExpenseDto;
import com.jmnoland.expensetrackerapi.models.entities.Expense;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpenseDto entityToDto(Expense obj);
    List<ExpenseDto> entityToDto(List<Expense> list);

    Expense dtoToEntity(ExpenseDto obj);
    List<Expense> dtoToEntity(List<ExpenseDto> list);
}
