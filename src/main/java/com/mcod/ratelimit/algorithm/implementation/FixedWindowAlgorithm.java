package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.model.config.Rule;

public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    @Override
    public boolean tryAcquire(String key, Rule rule) {
        return false;
    }
}
