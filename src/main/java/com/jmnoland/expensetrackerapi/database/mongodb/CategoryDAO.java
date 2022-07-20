package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryDAO extends MongoRepository<Category, String> {
}
