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

    public List<PaymentType> getPaymentTypes(String userId) {
        return this.paymentTypeDAO.findPaymentTypeByUserId(userId);
    }

    public boolean paymentTypeExistsId(String paymentTypeId) {
        return this.paymentTypeDAO.existsById(paymentTypeId);
    }

    public boolean paymentTypeExists(String paymentTypeName) {
        return this.paymentTypeDAO.paymentTypeExistsByName(paymentTypeName);
    }

    public void insert(PaymentType paymentType) {
        this.paymentTypeDAO.insert(paymentType);
    }

    public void update(PaymentType paymentType) {
        this.paymentTypeDAO.save(paymentType);
    }

    public void delete(PaymentType paymentType) {
        this.paymentTypeDAO.delete(paymentType);
    }
}
