package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseDAO extends MongoRepository<Expense, String> {
    @Query("{'clientId' : ?0}")
    List<Expense> findExpensesByClientId(String clientId);

    @Query("{'clientId' : ?0, 'expenseId' : ?0}")
    Optional<Expense> findExpenseById(String clientId, String expenseId);
}
