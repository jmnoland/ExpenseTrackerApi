package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.PaymentTypeServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.PaymentTypeMapper;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import com.jmnoland.expensetrackerapi.validators.paymenttype.CreatePaymentTypeValidator;
import com.jmnoland.expensetrackerapi.validators.paymenttype.UpdatePaymentTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService implements PaymentTypeServiceInterface {
    private final PaymentTypeRepositoryInterface paymentTypeRepository;
    private final PaymentTypeMapper mapper;
    @Autowired
    public PaymentTypeService(PaymentTypeRepositoryInterface paymentTypeRepository,
                              PaymentTypeMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.mapper = paymentTypeMapper;
    }

    public ServiceResponse<List<PaymentTypeDto>> getPaymentTypes(String clientId) {
        List<PaymentType> paymentTypes = this.paymentTypeRepository.getPaymentTypes(clientId);
        List<PaymentTypeDto> list = this.mapper.entityToDto(paymentTypes);

        return new ServiceResponse<>(list, true, null);
    }

    public ServiceResponse<PaymentTypeDto> insert(PaymentTypeDto paymentType, boolean archivePaymentType) {
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExists(paymentType.name);

        List<ValidationError> validationErrors = new CreatePaymentTypeValidator()
                .validate(paymentType, paymentTypeExists, archivePaymentType);

        if (archivePaymentType) {
            this.archivePaymentType(paymentType.paymentTypeId);
        }

        PaymentType newPaymentType = null;
        if (validationErrors.isEmpty()) {
            newPaymentType = this.mapper.dtoToEntity(paymentType);
            try {
                this.paymentTypeRepository.insert(newPaymentType);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(newPaymentType),
                validationErrors.isEmpty(),
                validationErrors
        );
    }

    public ServiceResponse<String> archivePaymentType(String paymentTypeId) {
        this.paymentTypeRepository.archivePaymentType(paymentTypeId);
        return new ServiceResponse<>(paymentTypeId, true, null);
    }

    public void delete(PaymentTypeDto paymentType) {
        PaymentType existing = this.mapper.dtoToEntity(paymentType);
        this.paymentTypeRepository.delete(existing);
    }

    public ServiceResponse<PaymentTypeDto> update(PaymentTypeDto paymentType) {
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExists(paymentType.paymentTypeId);

        List<ValidationError> validationErrors = new UpdatePaymentTypeValidator()
                .validate(paymentType, paymentTypeExists);

        PaymentType updatedPaymentType = null;
        if (validationErrors.isEmpty()) {
            updatedPaymentType = this.mapper.dtoToEntity(paymentType);
            try {
                this.paymentTypeRepository.update(updatedPaymentType);
            } catch (Exception e) {
                return new ServiceResponse<>(null, false, null);
            }
        }

        return new ServiceResponse<>(
                this.mapper.entityToDto(updatedPaymentType),
                validationErrors.isEmpty(),
                validationErrors
        );
    }
}
