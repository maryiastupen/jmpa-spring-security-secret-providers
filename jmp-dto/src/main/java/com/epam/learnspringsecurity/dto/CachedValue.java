package com.epam.learnspringsecurity.dto;

import java.time.LocalDateTime;

/**
 * Cached Value POJO
 */
public class CachedValue {

    private int attempts;

    private LocalDateTime blockedTimestamp;

    public CachedValue(int attempts, LocalDateTime blockedTimestamp) {
        this.attempts = attempts;
        this.blockedTimestamp = blockedTimestamp;
    }

    public CachedValue() {
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setBlockedTimestamp(LocalDateTime blockedTimestamp) {
        this.blockedTimestamp = blockedTimestamp;
    }

    public LocalDateTime getBlockedTimestamp() {
        return blockedTimestamp;
    }

}
