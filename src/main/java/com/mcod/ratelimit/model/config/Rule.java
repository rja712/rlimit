package com.mcod.ratelimit.model.config;

import com.mcod.ratelimit.algorithm.AlgorithmProviderType;

public interface Rule {
    AlgorithmProviderType getAlgorithmType();

    FallbackPolicy getFallbackPolicy();
}
