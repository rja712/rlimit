package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.inmemorystore.TokenBucketStore;
import com.mcod.ratelimit.algorithm.inmemorystore.TokenBucketStore.TokenBucketEntry;
import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.Rule;
import com.mcod.ratelimit.config.model.TokenBucketRule;

import java.util.Map;

public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private static final TokenBucketAlgorithm INSTANCE = new TokenBucketAlgorithm();

    private TokenBucketAlgorithm() {
    }

    public static TokenBucketAlgorithm getInstance() {
        return INSTANCE;
    }

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

    @Override
    public void clean(ConfigManager configManager) {
        long now = System.currentTimeMillis();

        for (Map.Entry<String, TokenBucketEntry> mapEntry : store.getEntries().entrySet()) {
            String key = mapEntry.getKey();
            TokenBucketEntry entry = mapEntry.getValue();

            // quick check without the lock
            if (!isFullyRefilled(configManager, key, entry, now)) {
                continue;
            }

            synchronized (entry) {
                // check again, the entry may have been used while waiting for the lock
                if (isFullyRefilled(configManager, key, entry, now)) {
                    store.getEntries().remove(key, entry);
                }
            }
        }
    }

    // a full bucket is the same as a new one, so evicting it changes nothing
    private boolean isFullyRefilled(ConfigManager configManager, String key, TokenBucketEntry entry, long now) {
        if (entry.lastWithdrawnAt == 0) {
            return false;
        }

        String apiName = key.substring(key.indexOf(':') + 1);
        if (!(configManager.getRule(apiName) instanceof TokenBucketRule rule)) {
            return true;
        }

        long refilled = (now - entry.lastWithdrawnAt) * rule.getRefillRate() / 1000;
        return entry.tokens + refilled >= rule.getMaxTokens();
    }
}
