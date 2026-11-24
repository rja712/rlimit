package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.model.config.Configuration;
import com.mcod.ratelimit.model.config.FallbackPolicy;
import com.mcod.ratelimit.model.config.FixedWindowRule;
import com.mcod.ratelimit.model.config.TokenBucketRule;

import java.util.Map;

public class HardCodedConfigManager implements ConfigManager {

    @Override
    public Configuration getConfiguration() {
        // TODO: hard coded values for now
        return new Configuration(
                new FixedWindowRule(10, FallbackPolicy.FAIL_OPEN),
                Map.of("createOrder", new TokenBucketRule(100, 10, FallbackPolicy.FAIL_CLOSE)));
    }
}
