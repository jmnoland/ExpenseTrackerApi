package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.PaymentTypeServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.PaymentTypeMapper;
import com.jmnoland.expensetrackerapi.models.dtos.PaymentTypeDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdatePaymentTypeRequest;
import com.jmnoland.expensetrackerapi.validators.paymenttype.CreatePaymentTypeValidator;
import com.jmnoland.expensetrackerapi.validators.paymenttype.UpdatePaymentTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentTypeService implements PaymentTypeServiceInterface {
    private final PaymentTypeRepositoryInterface paymentTypeRepository;
    private final PaymentTypeMapper mapper;
    private final DateProviderInterface dateProvider;
    @Autowired
    public PaymentTypeService(PaymentTypeRepositoryInterface paymentTypeRepository,
                              PaymentTypeMapper paymentTypeMapper,
                              DateProviderInterface dateProvider) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.mapper = paymentTypeMapper;
        this.dateProvider = dateProvider;
    }

    public ServiceResponse<List<PaymentTypeDto>> getPaymentTypes(String clientId) {
        List<PaymentType> paymentTypes = this.paymentTypeRepository.getPaymentTypes(clientId);
        List<PaymentTypeDto> list = this.mapper.entityToDto(paymentTypes);

        return new ServiceResponse<>(list, true, null);
    }

    public ServiceResponse<PaymentTypeDto> insert(CreateUpdatePaymentTypeRequest payload) {
        PaymentTypeDto paymentType = new PaymentTypeDto(UUID.randomUUID().toString(),
                payload.clientId,
                true,
                payload.name,
                payload.charge,
                dateProvider.getDateNow().getTime());

        boolean paymentTypeExists = this.paymentTypeRepository.paymentTypeExists(paymentType.name);

        List<ValidationError> validationErrors = new CreatePaymentTypeValidator()
                .validate(paymentType, paymentTypeExists, payload.archivePaymentType);

        if (payload.archivePaymentType) {
            this.archivePaymentType(paymentType.paymentTypeId, paymentType.clientId);
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

    public ServiceResponse<String> archivePaymentType(String paymentTypeId, String clientId) {
        this.paymentTypeRepository.archivePaymentType(paymentTypeId);
        return new ServiceResponse<>(paymentTypeId, true, null);
    }

    public void delete(PaymentTypeDto paymentType) {
        PaymentType existing = this.mapper.dtoToEntity(paymentType);
        this.paymentTypeRepository.delete(existing);
    }

    public ServiceResponse<PaymentTypeDto> update(CreateUpdatePaymentTypeRequest payload) {
        PaymentTypeDto paymentType = new PaymentTypeDto(payload.paymentTypeId,
                payload.clientId,
                true,
                payload.name,
                payload.charge,
                dateProvider.getDateNow().getTime());

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
