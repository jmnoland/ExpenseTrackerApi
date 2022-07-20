package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentTypeDAO extends MongoRepository<PaymentType, String> {
}
