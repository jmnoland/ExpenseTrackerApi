package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.PaymentType;

import java.util.List;

public interface PaymentTypeRepositoryInterface {

    List<PaymentType> getPaymentTypes(String clientId);

    boolean paymentTypeExistsId(String paymentTypeId);

    boolean paymentTypeExists(String paymentTypeName);

    void insert(PaymentType paymentType);

    void update(PaymentType paymentType);

    void delete(PaymentType paymentType);
}
