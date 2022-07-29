package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PaymentTypeDAO extends MongoRepository<PaymentType, String> {
    @Query("{'userId' : ?0}")
    List<PaymentType> findPaymentTypeByUserId(String userId);
    @Query("{'name' : ?0}")
    boolean paymentTypeExistsByName(String name);
}
