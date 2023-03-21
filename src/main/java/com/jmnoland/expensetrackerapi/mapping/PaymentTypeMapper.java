package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;

import com.jmnoland.expensetrackerapi.models.responses.PaymentTypeActionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {

    PaymentTypeMapper INSTANCE = Mappers.getMapper(PaymentTypeMapper.class);

    PaymentTypeDto entityToDto(PaymentType obj);
    List<PaymentTypeDto> entityToDto(List<PaymentType> list);

    PaymentType dtoToEntity(PaymentTypeDto obj);
    List<PaymentType> dtoToEntity(List<PaymentTypeDto> list);

    PaymentTypeActionResponse dtoToResponse(PaymentTypeDto obj, List<ValidationError> validationErrors);
}
