package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseDAO extends MongoRepository<Expense, String> {
    @Query("{'clientId' : ?0}")
    List<Expense> findExpensesByClientId(String clientId, Sort sort);

    @Query("{'clientId' : ?0, 'expenseId' : ?1}")
    Optional<Expense> findExpenseById(String clientId, String expenseId);

    @Query("{'clientId' : ?0, 'date' : { '$gte': ?1, '$lt': ?2 } }")
    List<Expense> findExpenseBetween(String clientId, Date startDate, Date endDate, Sort sort);
}
