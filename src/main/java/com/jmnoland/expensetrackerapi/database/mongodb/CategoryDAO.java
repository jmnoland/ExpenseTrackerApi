package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryDAO extends MongoRepository<Category, String> {
    @Query("{'clientId' : ?0}")
    List<Category> findCategoriesByClientId(String clientId, Sort sort);
}
