package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.PaymentTypeDAO;
import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.PaymentTypeRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentTypeRepository implements PaymentTypeRepositoryInterface {

    private final PaymentTypeDAO paymentTypeDAO;
    private final DateProviderInterface dateProvider;

    @Autowired
    public PaymentTypeRepository(PaymentTypeDAO paymentTypeDAO,
                                 DateProviderInterface dateProvider) {
        this.paymentTypeDAO = paymentTypeDAO;
        this.dateProvider = dateProvider;
    }

    public List<PaymentType> getPaymentTypes(String clientId) {
        return this.paymentTypeDAO.findPaymentTypeByClientId(clientId);
    }

    public boolean paymentTypeExistsId(String paymentTypeId) {
        return this.paymentTypeDAO.existsById(paymentTypeId);
    }

    public boolean paymentTypeExists(String paymentTypeName) {
        try {
            return this.paymentTypeDAO.paymentTypeExistsByName(paymentTypeName);
        } catch (Exception e) {
            return false;
        }
    }

    public void archivePaymentType(String paymentTypeId) {
        Optional<PaymentType> response = this.paymentTypeDAO.findById(paymentTypeId);
        if (!response.isPresent()) return;

        PaymentType entity = response.get();
        entity.setActive(false);
        entity.setArchivedAt(this.dateProvider.getDateNow().getTime());

        this.paymentTypeDAO.save(entity);
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
