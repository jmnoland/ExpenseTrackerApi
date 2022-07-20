package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.RecurringExpense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecurringExpenseDAO extends MongoRepository<RecurringExpense, String> {
}
