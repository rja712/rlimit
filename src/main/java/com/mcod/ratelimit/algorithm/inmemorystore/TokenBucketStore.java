package com.mcod.ratelimit.algorithm.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketStore {

    public static final class TokenBucketEntry {
        public int tokens;
        public long lastWithdrawnAt;
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
}
