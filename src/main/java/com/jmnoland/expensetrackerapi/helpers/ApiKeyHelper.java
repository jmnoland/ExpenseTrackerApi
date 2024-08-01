package com.jmnoland.expensetrackerapi.helpers;

import com.jmnoland.expensetrackerapi.models.dtos.ValidateApiKeyDto;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ApiKeyHelper {

    public static String getBase64String(HttpServletRequest request) {
        String fullHeader = request.getHeader("Authorization");
        if (fullHeader == null) return "";

        int index = fullHeader.indexOf(" ");
        return fullHeader.substring(index + 1);
    }

    public static ValidateApiKeyDto decodeAuthHeader(String header) {
        byte[] decoded = Base64.getDecoder().decode(header);
        String apiKeyString = new String(decoded, StandardCharsets.UTF_8);

        List<String> keyParts = new ArrayList<>();
        keyParts.add(apiKeyString.substring(0, apiKeyString.indexOf(":")));
        keyParts.add(apiKeyString.substring(apiKeyString.indexOf(":") + 1));

        return new ValidateApiKeyDto(keyParts.get(0), keyParts.get(1));
    }

    public static String generateApiKey(int keyLen) {
        if (keyLen < 8) keyLen = 8;
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[keyLen/8];
        random.nextBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String hashSecret(String secret) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hash);
        return number.toString(16);
    }
}
