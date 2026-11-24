package com.mcod.ratelimit.cleaner;

import com.mcod.ratelimit.algorithm.AlgorithmProviderType;
import com.mcod.ratelimit.algorithm.RateLimitAlgorithmFactory;
import com.mcod.ratelimit.config.ConfigManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanerScheduler {

    private static final CleanerScheduler INSTANCE = new CleanerScheduler();

    private ConfigManager configManager;

    private boolean started;

    private CleanerScheduler() {
    }

    public static CleanerScheduler getInstance() {
        return INSTANCE;
    }

    public synchronized void start(long intervalSeconds, ConfigManager configManager) {
        if (started) {
            return;
        }
        started = true;

        this.configManager = configManager;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "rate-limit-cleaner");
            thread.setDaemon(true);
            return thread;
        });

        executor.scheduleWithFixedDelay(this::cleanAll, intervalSeconds, intervalSeconds, TimeUnit.SECONDS);
    }

    private void cleanAll() {
        for (AlgorithmProviderType type : AlgorithmProviderType.values()) {
            try {
                RateLimitAlgorithmFactory.get(type).clean(configManager);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
