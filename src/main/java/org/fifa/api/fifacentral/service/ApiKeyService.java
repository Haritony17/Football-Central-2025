package org.fifa.api.fifacentral.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiKeyService {
    private final Set<String> validKeys = ConcurrentHashMap.newKeySet();

    public String generateKey() {
        String key = "FIFA-" + UUID.randomUUID().toString();
        validKeys.add(key);
        return key;
    }

    public boolean isValid(String key) {
        return key != null && validKeys.contains(key);
    }
}