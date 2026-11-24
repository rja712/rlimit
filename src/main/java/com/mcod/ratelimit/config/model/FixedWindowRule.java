package com.mcod.ratelimit.config.model;

import com.mcod.ratelimit.algorithm.AlgorithmProviderType;

public final class FixedWindowRule implements Rule {

    private final int requestsPerSecond;
    private final FallbackPolicy fallbackPolicy;

    public FixedWindowRule(int requestsPerSecond, FallbackPolicy fallbackPolicy) {
        this.requestsPerSecond = requestsPerSecond;
        this.fallbackPolicy = fallbackPolicy;
    }

    public int getRequestsPerSecond() {
        return requestsPerSecond;
    }

    @Override
    public AlgorithmProviderType getAlgorithmType() {
        return AlgorithmProviderType.FIXED_WINDOW;
    }

    @Override
    public FallbackPolicy getFallbackPolicy() {
        return fallbackPolicy;
    }
}
