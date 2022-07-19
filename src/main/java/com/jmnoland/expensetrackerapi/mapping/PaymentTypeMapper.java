package com.jmnoland.expensetrackerapi.mapping;

import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaymentTypeMapper {

    PaymentTypeMapper INSTANCE = Mappers.getMapper(PaymentTypeMapper.class);

    PaymentTypeDto entityToDto(PaymentType obj);
    List<PaymentTypeDto> entityToDto(List<PaymentType> list);

    PaymentType dtoToEntity(PaymentTypeDto obj);
    List<PaymentType> dtoToEntity(List<PaymentTypeDto> list);
}
