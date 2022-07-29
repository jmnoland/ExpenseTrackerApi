package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecurringExpenseMapper {

    RecurringExpenseMapper INSTANCE = Mappers.getMapper(RecurringExpenseMapper.class);

    RecurringExpenseDto entityToDto(RecurringExpense obj);
    List<RecurringExpenseDto> entityToDto(List<RecurringExpense> list);

    RecurringExpense dtoToEntity(RecurringExpenseDto obj);
    List<RecurringExpense> dtoToEntity(List<RecurringExpenseDto> list);
}
