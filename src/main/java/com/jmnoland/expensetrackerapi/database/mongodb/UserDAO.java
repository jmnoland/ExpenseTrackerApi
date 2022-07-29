package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserDAO extends MongoRepository<User, String> {
    @Query("{'email' : ?0}")
    Optional<User> findByEmail(String email);
}
