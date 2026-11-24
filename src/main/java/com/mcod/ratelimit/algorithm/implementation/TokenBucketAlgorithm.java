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

            if (entry.isNew()) {
                entry.update(tokenBucketRule.getMaxTokens() - 1, now);
                return true;
            }

            int available = entry.availableTokens(tokenBucketRule.getMaxTokens(), tokenBucketRule.getRefillRate(), now);

            if (available < 1) {
                return false;
            }

            entry.update(available - 1, now);
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
        if (entry.isNew()) {
            return false;
        }

        String apiName = key.substring(key.indexOf(':') + 1);
        if (!(configManager.getRule(apiName) instanceof TokenBucketRule rule)) {
            return true;
        }

        return entry.availableTokens(rule.getMaxTokens(), rule.getRefillRate(), now) >= rule.getMaxTokens();
    }
}
