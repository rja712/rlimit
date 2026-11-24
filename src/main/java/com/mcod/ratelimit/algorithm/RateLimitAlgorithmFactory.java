package com.mcod.ratelimit.algorithm;

import com.mcod.ratelimit.algorithm.implementation.FixedWindowAlgorithm;
import com.mcod.ratelimit.algorithm.implementation.TokenBucketAlgorithm;

public final class RateLimitAlgorithmFactory {

    private RateLimitAlgorithmFactory() {
    }

    public static RateLimitAlgorithm create(AlgorithmProviderType type) {
        return switch (type) {
            case FIXED_WINDOW -> new FixedWindowAlgorithm();
            case TOKEN_BUCKET -> new TokenBucketAlgorithm();
        };
    }
}
