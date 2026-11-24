package com.mcod.ratelimit.algorithm.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowStore {

    private static final FixedWindowStore INSTANCE = new FixedWindowStore();

    private FixedWindowStore() {
    }

    public static FixedWindowStore getInstance() {
        return INSTANCE;
    }

    private final Map<String, FixedWindowEntry> entries = new ConcurrentHashMap<>();

    public static final class FixedWindowEntry {

        private final int count;
        private final long lastAccessedAt;

        public FixedWindowEntry(int count, long lastAccessedAt) {
            this.count = count;
            this.lastAccessedAt = lastAccessedAt;
        }

        public int getCount() {
            return count;
        }

        public long getLastAccessedAt() {
            return lastAccessedAt;
        }
    }
}
