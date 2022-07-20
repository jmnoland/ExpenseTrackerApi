package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.PaymentTypeDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentTypeRepository implements PaymentTypeRepositoryInterface {

    private final PaymentTypeDAO paymentTypeDAO;

    @Autowired
    public PaymentTypeRepository(PaymentTypeDAO paymentTypeDAO) {
        this.paymentTypeDAO = paymentTypeDAO;
    }

    @Override
    public List<PaymentType> getPaymentTypes(String userId) {
        return null;
    }

    @Override
    public boolean paymentTypeExistsId(String paymentTypeId) {
        return false;
    }

    @Override
    public boolean paymentTypeExists(String paymentTypeName) {
        return false;
    }

    @Override
    public void insert(PaymentType paymentType) {

    }

    @Override
    public void update(PaymentType paymentType) {

    }

    @Override
    public void delete(PaymentType paymentType) {

    }
}
