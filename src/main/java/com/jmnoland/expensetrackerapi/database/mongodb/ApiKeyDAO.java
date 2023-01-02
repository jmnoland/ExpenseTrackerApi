package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ApiKeyDAO extends MongoRepository<ApiKey, String> {
    @Query("{'KeyId' : ?0}")
    ApiKey findApiKeyById(String keyId);
    @Query("{'keyHash' : ?0}")
    ApiKey findApiKeyByHash(String keyHash);
}
