package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.model.config.Configuration;
import com.mcod.ratelimit.model.config.FallbackPolicy;
import com.mcod.ratelimit.model.config.FixedWindowRule;
import com.mcod.ratelimit.model.config.Rule;
import com.mcod.ratelimit.model.config.TokenBucketRule;

import java.util.Map;

public class HardCodedConfigManager implements ConfigManager {

    private static final Configuration configuration = buildConfiguration();

    private static Configuration buildConfiguration() {
        Rule defaultRule = new FixedWindowRule(10, FallbackPolicy.FAIL_OPEN);
        Map<String, Rule> apiRules = Map.of("createOrder", new TokenBucketRule(100, 10, FallbackPolicy.FAIL_CLOSE));
        return new Configuration(defaultRule, apiRules);
    }

    @Override
    public Rule getRule(String apiName) {
        return configuration.getRule(apiName);
    }
}
