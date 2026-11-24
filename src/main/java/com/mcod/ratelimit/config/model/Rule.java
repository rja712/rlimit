package com.mcod.ratelimit.config.model;

import com.mcod.ratelimit.algorithm.AlgorithmProviderType;

public interface Rule {
    AlgorithmProviderType getAlgorithmType();

    FallbackPolicy getFallbackPolicy();
}
