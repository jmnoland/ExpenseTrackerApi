package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PaymentTypeDAO extends MongoRepository<PaymentType, String> {
    @Query("{'clientId' : ?0}")
    List<PaymentType> findPaymentTypeByClientId(String clientId);
    @Query("{'name' : ?0}")
    boolean paymentTypeExistsByName(String name);
}
