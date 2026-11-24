package com.mcod.ratelimit.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketStore {

    private static final TokenBucketStore INSTANCE = new TokenBucketStore();

    private TokenBucketStore() {
    }

    public static TokenBucketStore getInstance() {
        return INSTANCE;
    }

    private final Map<String, TokenBucketEntry> entries = new ConcurrentHashMap<>();

    public static final class TokenBucketEntry {

        private final int tokens;
        private final long lastWithdrawnAt;

        public TokenBucketEntry(int tokens, long lastWithdrawnAt) {
            this.tokens = tokens;
            this.lastWithdrawnAt = lastWithdrawnAt;
        }

        public int getTokens() {
            return tokens;
        }

        public long getLastWithdrawnAt() {
            return lastWithdrawnAt;
        }
    }
}
