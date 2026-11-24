package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.inmemorystore.FixedWindowStore;
import com.mcod.ratelimit.algorithm.inmemorystore.FixedWindowStore.FixedWindowEntry;
import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.FixedWindowRule;
import com.mcod.ratelimit.config.model.Rule;

import java.util.Map;

public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    private static final FixedWindowAlgorithm INSTANCE = new FixedWindowAlgorithm();

    private FixedWindowAlgorithm() {
    }

    public static FixedWindowAlgorithm getInstance() {
        return INSTANCE;
    }

    private static final long WINDOW_MILLIS = 1000;

    private final FixedWindowStore store = FixedWindowStore.getInstance();

    @Override
    public boolean isAllowed(String key, Rule rule) {

        FixedWindowRule fixedWindowRule = (FixedWindowRule) rule;

        long now = System.currentTimeMillis();
        long windowStart = now - now % WINDOW_MILLIS;

        FixedWindowEntry entry = store.getOrCreate(key);

        synchronized (entry) {
            if (entry.isStale(windowStart)) {
                entry.startWindow(now);
                return true;
            }

            if (entry.isLimitReached(fixedWindowRule.getRequestsPerSecond())) {
                return false;
            }

            entry.increment(now);
            return true;
        }
    }

    @Override
    public void clean(ConfigManager configManager) {
        long now = System.currentTimeMillis();
        long windowStart = now - now % WINDOW_MILLIS;

        for (Map.Entry<String, FixedWindowEntry> mapEntry : store.getEntries().entrySet()) {
            FixedWindowEntry entry = mapEntry.getValue();

            // quick check without the lock
            if (!entry.isStale(windowStart)) {
                continue;
            }

            synchronized (entry) {
                // check again, the entry may have been used while waiting for the lock
                if (entry.isStale(windowStart)) {
                    store.getEntries().remove(mapEntry.getKey(), entry);
                }
            }
        }
    }
}
