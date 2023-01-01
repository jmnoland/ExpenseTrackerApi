package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.ApiKeyDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApiKeyRepository implements ApiKeyRepositoryInterface {

    private final ApiKeyDAO apiKeyDAO;
    @Autowired
    public ApiKeyRepository(ApiKeyDAO apiKeyDAO) {
        this.apiKeyDAO = apiKeyDAO;
    }

    public ApiKey getApiKeyByHash(String keyHash) { return this.apiKeyDAO.findApiKeyByHash(keyHash); }
    public ApiKey findApiKeyByClientId(String clientId) { return this.apiKeyDAO.findApiKeyByClientId(clientId); }
    public List<String> getAllClientIds() {
        List<ApiKey> keys = this.apiKeyDAO.findAll();

        List<String> clientIds = new ArrayList<>();
        for(ApiKey apikey : keys) {
            clientIds.add(apikey.getId());
        }

        return clientIds;
    }
    public void update(ApiKey apiKey) { this.apiKeyDAO.save(apiKey); }
    public void insert(ApiKey apiKey) { this.apiKeyDAO.insert(apiKey); }
}
