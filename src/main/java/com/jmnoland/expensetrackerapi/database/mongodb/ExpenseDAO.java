package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseDAO extends MongoRepository<Expense, String> {
}
