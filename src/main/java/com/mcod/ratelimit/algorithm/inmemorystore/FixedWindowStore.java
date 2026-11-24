package com.mcod.ratelimit.algorithm.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowStore {

    public static final class FixedWindowEntry {
        private int count;
        private long lastAccessedAt;

        public boolean isStale(long windowStart) {
            return lastAccessedAt < windowStart;
        }

        public boolean isLimitReached(int limit) {
            return count >= limit;
        }

        public void startWindow(long now) {
            count = 1;
            lastAccessedAt = now;
        }

        public void increment(long now) {
            count++;
            lastAccessedAt = now;
        }
    }

    private static final FixedWindowStore INSTANCE = new FixedWindowStore();

    private FixedWindowStore() {
    }

    public static FixedWindowStore getInstance() {
        return INSTANCE;
    }

    // key: clientId + apiName
    private final Map<String, FixedWindowEntry> entries = new ConcurrentHashMap<>();

    public FixedWindowEntry getOrCreate(String key) {
        return entries.computeIfAbsent(key, k -> new FixedWindowEntry());
    }

    public Map<String, FixedWindowEntry> getEntries() {
        return entries;
    }
}
