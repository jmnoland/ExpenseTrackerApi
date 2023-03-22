package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.LineItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface LineItemDAO extends MongoRepository<LineItem, String> {
    @Query("{'expenseId' : ?0}")
    Optional<LineItem> findLineItemByExpenseId(String expenseId);
}
