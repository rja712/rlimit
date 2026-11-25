package com.mcod.ratelimit.algorithm.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketStore {

    public static final class TokenBucketEntry {
        private int tokens;
        private long lastWithdrawnAt;

        public boolean isNew() {
            return lastWithdrawnAt == 0;
        }

        public int availableTokens(int maxTokens, int refillRate, long now) {
            long refilled = (now - lastWithdrawnAt) * refillRate / 1000;
            return (int) Math.min(maxTokens, tokens + refilled);
        }

        public void update(int tokens, long now) {
            this.tokens = tokens;
            this.lastWithdrawnAt = now;
        }
    }

    private static final TokenBucketStore INSTANCE = new TokenBucketStore();

    private TokenBucketStore() {
    }

    public static TokenBucketStore getInstance() {
        return INSTANCE;
    }

    // key: clientId + apiName
    private final Map<String, TokenBucketEntry> entries = new ConcurrentHashMap<>();

    public TokenBucketEntry getOrCreate(String key) {
        return entries.computeIfAbsent(key, k -> new TokenBucketEntry());
    }

    public Map<String, TokenBucketEntry> getEntries() {
        return entries;
    }

    // takes each entry lock once, so work already running on that entry finishes first
    public void lockEntries() {
        for (TokenBucketEntry entry : entries.values()) {
            synchronized (entry) {
                continue;
            }
        }
    }
}
