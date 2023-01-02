package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.ApiKey;

import java.util.List;

public interface ApiKeyRepositoryInterface {
    List<String> getAllClientIds();
    ApiKey getApiKeyByHash(String keyHash);
    ApiKey findApiKeyById(String id);
    void update(ApiKey apiKey);
    void insert(ApiKey apiKey);
}
