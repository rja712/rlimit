package com.mcod.ratelimit;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.RateLimitAlgorithmFactory;
import com.mcod.ratelimit.cleaner.CleanerScheduler;
import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.ConfigManagerFactory;
import com.mcod.ratelimit.config.ConfigProviderType;
import com.mcod.ratelimit.config.model.Rule;

public final class RateLimiter {

    private static final long SHUTDOWN_TIMEOUT_MILLIS = 5000;

    private static RateLimiter instance;

    private final ConfigManager configManager;

    private volatile boolean shutdown;

    private RateLimiter(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static synchronized RateLimiter initialize(ConfigProviderType providerType) {
        if (instance == null) {
            ConfigManager configManager = ConfigManagerFactory.create(providerType);
            instance = new RateLimiter(configManager);
            CleanerScheduler.getInstance().start(configManager.getCleaningIntervalSeconds(), configManager);
            Runtime.getRuntime().addShutdownHook(new Thread(instance::shutdown, "rate-limit-shutdown"));
        }
        return instance;
    }

    public synchronized void shutdown() {
        if (shutdown) {
            return;
        }
        shutdown = true;

        // graceful part runs on its own thread, because waiting for a lock cannot be interrupted
        Thread graceful = new Thread(CleanerScheduler.getInstance()::shutdown, "rate-limit-graceful-shutdown");
        graceful.setDaemon(true);
        graceful.start();

        try {
            graceful.join(SHUTDOWN_TIMEOUT_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (graceful.isAlive()) {
            // abrupt stop: give up waiting for in flight calls
            CleanerScheduler.getInstance().shutdownNow();
        }
    }

    public boolean isAllowed(String clientId, String apiName) {
        if (shutdown) {
            throw new IllegalStateException("RateLimiter is shut down");
        }

        Rule rule = configManager.getRule(apiName);
        RateLimitAlgorithm algorithm = RateLimitAlgorithmFactory.get(rule.getAlgorithmType());
        return algorithm.isAllowed(clientId + ":" + apiName, rule);
    }
}
