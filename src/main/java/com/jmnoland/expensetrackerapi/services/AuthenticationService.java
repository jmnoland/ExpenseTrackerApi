package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.helpers.ApiKeyHelper;
import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.ApiKeyMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ApiKeyDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidateApiKeyDto;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.entities.ApiKey;
import com.jmnoland.expensetrackerapi.models.responses.ApiKeyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class AuthenticationService implements AuthenticationServiceInterface {

    private final ApiKeyRepositoryInterface apiKeyRepository;
    private final DateProviderInterface dateProvider;
    private final ApiKeyMapper mapper;

    @Autowired
    public AuthenticationService(ApiKeyRepositoryInterface apiKeyRepository,
                                 DateProviderInterface dateProvider,
                                 ApiKeyMapper mapper) {
        this.apiKeyRepository = apiKeyRepository;
        this.dateProvider = dateProvider;
        this.mapper = mapper;
    }

    public ServiceResponse<ApiKeyResponse> createClientApiKey() {
        return createClientApiKey(null);
    }

    public ServiceResponse<ApiKeyResponse> createClientApiKey(String clientId) {
        String secret = ApiKeyHelper.generateApiKey(256);
        String hash;

        try {
            hash = ApiKeyHelper.hashSecret(secret);
        } catch (NoSuchAlgorithmException e) {
            List<ValidationError> validationErrorList = new ArrayList<>();
            validationErrorList.add(new ValidationError("Hash", "Failed to create api key"));
            return new ServiceResponse<>(null, false, validationErrorList);
        }

        String id = UUID.randomUUID().toString();
        if (clientId == null)
            clientId = UUID.randomUUID().toString();

        Date dateNow = this.dateProvider.getDateNow().getTime();
        ApiKeyDto newKey = new ApiKeyDto(id, clientId, hash, true, dateNow, null);

        this.apiKeyRepository.insert(this.mapper.dtoToEntity(newKey));

        ApiKeyResponse response = new ApiKeyResponse(newKey.getKeyId(), secret, newKey.getClientId());
        return new ServiceResponse<>(response, true);
    }

    public ServiceResponse<ApiKeyResponse> createNewClientApiKey(String apiKeyHeader) {
        ValidateApiKeyDto apiKey = ApiKeyHelper.decodeAuthHeader(apiKeyHeader);

        ApiKey key = this.apiKeyRepository.findApiKeyById(apiKey.KeyId);
        key.setActive(false);
        key.setRevokedAt(this.dateProvider.getDateNow().getTime());

        ServiceResponse<ApiKeyResponse> response = createClientApiKey(key.getClientId());
        this.apiKeyRepository.update(key);

        return response;
    }

    public boolean validateApiKey(String apiKeyHeader, String clientId) {
        ValidateApiKeyDto apiKey = ApiKeyHelper.decodeAuthHeader(apiKeyHeader);

        ApiKey key = this.apiKeyRepository.findApiKeyById(apiKey.KeyId);
        if (key == null) return false;

        String hash;
        try {
            hash = ApiKeyHelper.hashSecret(apiKey.Secret);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        boolean keyMatch = Objects.equals(key.getKeyHash(), hash);
        boolean clientMatch = Objects.equals(key.getClientId(), clientId);
        return keyMatch && clientMatch;
    }
}
