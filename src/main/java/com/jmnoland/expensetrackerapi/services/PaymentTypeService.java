package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.UserRepositoryInterface;
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
    private final UserRepositoryInterface userRepositoryInterface;
    private final PaymentTypeMapper mapper;
    @Autowired
    public PaymentTypeService(PaymentTypeRepositoryInterface paymentTypeRepository,
                              UserRepositoryInterface userRepositoryInterface,
                              PaymentTypeMapper paymentTypeMapper) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.userRepositoryInterface = userRepositoryInterface;
        this.mapper = paymentTypeMapper;
    }

    public List<PaymentTypeDto> getPaymentTypes(String userId) {
        List<PaymentType> paymentTypes = this.paymentTypeRepository.getPaymentTypes(userId);
        return this.mapper.entityToDto(paymentTypes);
    }

    public ServiceResponse<PaymentTypeDto> insert(PaymentTypeDto paymentType) {
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExists(paymentType.name);
        boolean userExists = this.userRepositoryInterface.userExists(paymentType.userId);

        List<ValidationError> validationErrors = new CreatePaymentTypeValidator()
                .validate(paymentType, paymentTypeExists, userExists);

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

    public void delete(PaymentTypeDto paymentType) {
        PaymentType existing = this.mapper.dtoToEntity(paymentType);
        this.paymentTypeRepository.delete(existing);
    }

    public ServiceResponse<PaymentTypeDto> update(PaymentTypeDto paymentType) {
        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExists(paymentType.paymentTypeId);
        boolean userExists = this.userRepositoryInterface.userExists(paymentType.userId);

        List<ValidationError> validationErrors = new UpdatePaymentTypeValidator()
                .validate(paymentType, paymentTypeExists, userExists);

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
