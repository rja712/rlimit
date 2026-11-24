package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.inmemorystore.TokenBucketStore;
import com.mcod.ratelimit.algorithm.inmemorystore.TokenBucketStore.TokenBucketEntry;
import com.mcod.ratelimit.config.model.Rule;
import com.mcod.ratelimit.config.model.TokenBucketRule;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final TokenBucketStore store = TokenBucketStore.getInstance();

    @Override
    public boolean isAllowed(String key, Rule rule) {

        TokenBucketRule tokenBucketRule = (TokenBucketRule) rule;
        long now = System.currentTimeMillis();

        TokenBucketEntry entry = store.getOrCreate(key);

        synchronized (entry) {

            if (entry.lastWithdrawnAt == 0) {
                entry.tokens = tokenBucketRule.getMaxTokens() - 1;
                entry.lastWithdrawnAt = now;
                return true;
            }

            long refilled = (now - entry.lastWithdrawnAt) * tokenBucketRule.getRefillRate() / 1000;
            int available = (int) Math.min(tokenBucketRule.getMaxTokens(), entry.tokens + refilled);

            if (available < 1) {
                return false;
            }

            entry.tokens = available - 1;
            entry.lastWithdrawnAt = now;
            return true;
        }
    }
}
