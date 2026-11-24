package com.mcod.ratelimit;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.algorithm.RateLimitAlgorithmFactory;
import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.ConfigManagerFactory;
import com.mcod.ratelimit.config.ConfigProviderType;
import com.mcod.ratelimit.model.config.Rule;

public final class RateLimiter {

    private static RateLimiter instance;

    private final ConfigManager configManager;

    private RateLimiter(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public static synchronized RateLimiter initialize(ConfigProviderType providerType) {
        if (instance == null) {
            instance = new RateLimiter(ConfigManagerFactory.create(providerType));
        }
        return instance;
    }

    public boolean isAllowed(String clientId, String apiName) {
        Rule rule = configManager.getRule(apiName);
        RateLimitAlgorithm algorithm = RateLimitAlgorithmFactory.create(rule.getAlgorithmType());
        return algorithm.tryAcquire(clientId + "," + apiName, rule);
    }
}
