package com.mcod.ratelimit.algorithm;

import com.mcod.ratelimit.model.config.Rule;

public interface RateLimitAlgorithm {
    boolean tryAcquire(String key, Rule rule);
}
