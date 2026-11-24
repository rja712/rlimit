package com.mcod.ratelimit.algorithm.implementation;

import com.mcod.ratelimit.algorithm.RateLimitAlgorithm;
import com.mcod.ratelimit.model.config.Rule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FixedWindowAlgorithm implements RateLimitAlgorithm {


    @Override
    public boolean tryAcquire(String key, Rule rule) {
        return false;
    }
}
