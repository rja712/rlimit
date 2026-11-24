package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.inmemorystore.FixedWindowStore;
import com.mcod.ratelimit.algorithm.inmemorystore.FixedWindowStore.FixedWindowEntry;
import com.mcod.ratelimit.config.model.FixedWindowRule;
import com.mcod.ratelimit.config.model.Rule;

public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    private static final long WINDOW_MILLIS = 1000;

    private final FixedWindowStore store = FixedWindowStore.getInstance();

    @Override
    public boolean isAllowed(String key, Rule rule) {

        FixedWindowRule fixedWindowRule = (FixedWindowRule) rule;

        long now = System.currentTimeMillis();
        long windowStart = now - now % WINDOW_MILLIS;

        FixedWindowEntry entry = store.getOrCreate(key);

        synchronized (entry) {
            if (entry.lastAccessedAt < windowStart) {
                entry.count = 1;
                entry.lastAccessedAt = now;
                return true;
            }

            if (entry.count >= fixedWindowRule.getRequestsPerSecond()) {
                return false;
            }

            entry.count++;
            entry.lastAccessedAt = now;
            return true;
        }
    }
}
