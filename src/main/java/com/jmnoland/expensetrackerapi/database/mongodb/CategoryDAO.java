package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryDAO extends MongoRepository<Category, String> {
    @Query("{'userId' : ?0}")
    List<Category> findCategoriesByUserId(String userId);
}
