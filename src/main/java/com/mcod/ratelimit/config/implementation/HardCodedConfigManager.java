package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.Configuration;
import com.mcod.ratelimit.config.model.FallbackPolicy;
import com.mcod.ratelimit.config.model.FixedWindowRule;
import com.mcod.ratelimit.config.model.Rule;
import com.mcod.ratelimit.config.model.TokenBucketRule;

import java.util.Map;

public class HardCodedConfigManager implements ConfigManager {

    private static final Configuration configuration = buildConfiguration();

    private static Configuration buildConfiguration() {
        Rule defaultRule = new FixedWindowRule(10, FallbackPolicy.FAIL_OPEN);
        Map<String, Rule> apiRules = Map.of("createOrder", new TokenBucketRule(100, 10, FallbackPolicy.FAIL_CLOSE));
        return new Configuration(defaultRule, apiRules, 60);
    }

    @Override
    public Rule getRule(String apiName) {
        return configuration.getRule(apiName);
    }

    @Override
    public long getCleaningIntervalSeconds() {
        return configuration.getCleaningIntervalSeconds();
    }
}
