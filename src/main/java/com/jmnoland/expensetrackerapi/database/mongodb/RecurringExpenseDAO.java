package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecurringExpenseDAO extends MongoRepository<RecurringExpense, String> {
    @Query("{'clientId' : ?0}")
    List<RecurringExpense> findRecurringExpensesByClientId(String clientId);
}
