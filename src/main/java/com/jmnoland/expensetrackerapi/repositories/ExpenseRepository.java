package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.models.entities.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

//    @Query("{ name: '?0' }")
//    Expense findItemByName(String name);

}
