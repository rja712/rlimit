package com.mcod.ratelimit.algorithm;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.Rule;

public interface RateLimitAlgorithm {

    boolean isAllowed(String key, Rule rule);

    void clean(ConfigManager configManager);

}
