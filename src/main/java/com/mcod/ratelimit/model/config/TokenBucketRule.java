package com.mcod.ratelimit.model.config;

import com.mcod.ratelimit.algorithm.AlgorithmProviderType;

public final class TokenBucketRule implements Rule {

    private final int maxTokens;
    private final int refillRate;
    private final FallbackPolicy fallbackPolicy;

    public TokenBucketRule(int maxTokens, int refillRate, FallbackPolicy fallbackPolicy) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.fallbackPolicy = fallbackPolicy;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public int getRefillRate() {
        return refillRate;
    }

    @Override
    public AlgorithmProviderType getAlgorithmType() {
        return AlgorithmProviderType.TOKEN_BUCKET;
    }

    @Override
    public FallbackPolicy getFallbackPolicy() {
        return fallbackPolicy;
    }
}
