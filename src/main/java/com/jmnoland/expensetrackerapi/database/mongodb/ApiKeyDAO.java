package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ApiKeyDAO extends MongoRepository<ApiKey, String> {
    @Query("{'KeyId' : ?0, 'Active': true}")
    ApiKey findApiKeyById(String keyId);
    @Query("{'keyHash' : ?0, 'Active': true}")
    ApiKey findApiKeyByHash(String keyHash);
}
