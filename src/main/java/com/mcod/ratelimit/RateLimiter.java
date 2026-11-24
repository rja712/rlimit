package com.mcod.ratelimit;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.ConfigManagerFactory;
import com.mcod.ratelimit.config.ConfigProviderType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RateLimiter {

    private final ConfigManager configManager;

    public static RateLimiter initialize() {
        return new RateLimiter(ConfigManagerFactory.create(ConfigProviderType.HARDCODED));
    }

    public boolean isAllowed(String clientId, String apiName) {
        // TODO: look up the rule for (clientId, apiName) and apply the limiting logic.
        return true;
    }
}
