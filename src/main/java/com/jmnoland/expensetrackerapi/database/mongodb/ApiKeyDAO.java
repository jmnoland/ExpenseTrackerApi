package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ApiKeyDAO extends MongoRepository<ApiKey, String> {
    @Query("{'clientId' : ?0}")
    ApiKey findApiKeyByClientId(String clientId);
    @Query("{'keyHash' : ?0}")
    ApiKey findApiKeyByHash(String keyHash);
}
