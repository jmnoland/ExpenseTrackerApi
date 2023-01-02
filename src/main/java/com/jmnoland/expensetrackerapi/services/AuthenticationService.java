package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.AuthenticationServiceInterface;
import com.jmnoland.expensetrackerapi.mapping.ApiKeyMapper;
import com.jmnoland.expensetrackerapi.models.dtos.ApiKeyDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.dtos.ValidationError;
import com.jmnoland.expensetrackerapi.models.responses.ApiKeyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    private static String generateApiKey(int keyLen) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLen/8];
        random.nextBytes(bytes);
        return DatatypeConverter.printHexBinary(bytes);
    }

    private static String hashSecret(String secret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hash);
        return number.toString(16);
    }

    public ServiceResponse<ApiKeyResponse> createClientApiKey() {
        String secret = generateApiKey(256);
        String hash;

        try {
            hash = hashSecret(secret);
        } catch (NoSuchAlgorithmException e) {
            List<ValidationError> validationErrorList = new ArrayList<>();
            validationErrorList.add(new ValidationError("Hash", "Failed to create api key"));
            return new ServiceResponse<>(null, false, validationErrorList);
        }

        String id = UUID.randomUUID().toString();
        String clientId = UUID.randomUUID().toString();

        Date dateNow = this.dateProvider.getDateNow().getTime();
        ApiKeyDto newKey = new ApiKeyDto(id, clientId, hash, true, dateNow, null);

        this.apiKeyRepository.insert(this.mapper.dtoToEntity(newKey));

        ApiKeyResponse response = new ApiKeyResponse(newKey.getId(), secret, newKey.getClientId());
        return new ServiceResponse<>(response, true, null);
    }
}
