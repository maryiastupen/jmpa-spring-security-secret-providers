package com.epam.learnspringsecurity.service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.epam.learnspringsecurity.dto.CachedValue;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

/**
 * Service, responsible for dealing with login attempts
 */
@Service
public class LoginAttemptService {

    public static final int MAX_ATTEMPT = 3;
    public static final int BLOCK_DURATION_MINUTES = 5;

    private final LoadingCache<String, CachedValue> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(BLOCK_DURATION_MINUTES, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public CachedValue load(final String key) {
                                return new CachedValue(0, LocalDateTime.now());
                            }
                        });
    }

    /**
     * Updates value in the cache in case login is failed
     *
     * @param key key in the cache
     */
    public void loginFailed(final String key) {
        CachedValue cachedValue = new CachedValue();
        try {
            cachedValue = attemptsCache.get(key);
            cachedValue.setAttempts(cachedValue.getAttempts() + 1);
        } catch (final ExecutionException e) {
            cachedValue.setAttempts(0);
        }
        if (shouldBeBlocked(key) && cachedValue.getBlockedTimestamp() == null) {
            cachedValue.setBlockedTimestamp(LocalDateTime.now());
        }
        attemptsCache.put(key, cachedValue);
    }

    /**
     * Identifies whether value should be blocked
     *
     * @param key key in the cache
     * @return true in case value should be blocked
     */
    public boolean shouldBeBlocked(final String key) {
        try {
            return attemptsCache.get(key).getAttempts() >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }

    /**
     * Gets cached value by key
     *
     * @param key key in the cache
     * @return cached value by the key
     */
    public CachedValue getCachedValue(final String key) {
        return attemptsCache.getUnchecked(key);
    }

}
