package com.mcod.ratelimit.model.config;

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
    public FallbackPolicy getFallbackPolicy() {
        return fallbackPolicy;
    }
}
