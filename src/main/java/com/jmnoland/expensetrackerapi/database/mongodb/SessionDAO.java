package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface SessionDAO extends MongoRepository<Session, String> {

    @Query("{'userId' : ?0}")
    Optional<Session> findByUserId(String userId);
    @Query("{'sessionId' : ?0}")
    Optional<Session> findById(String sessionId);
}
