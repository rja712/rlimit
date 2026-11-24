package com.mcod.ratelimit.algorithm.inmemorystore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowStore {

    public static final class FixedWindowEntry {
        private int count;
        private long lastAccessedAt;
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
