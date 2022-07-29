package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExpenseDAO extends MongoRepository<Expense, String> {
    @Query("{'userId' : ?0}")
    List<Expense> findExpensesByUserId(String userId);
}
