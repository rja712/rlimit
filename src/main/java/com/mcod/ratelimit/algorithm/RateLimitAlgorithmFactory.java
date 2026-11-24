package com.mcod.ratelimit.algorithm;

import com.mcod.ratelimit.algorithm.implementation.FixedWindowAlgorithm;
import com.mcod.ratelimit.algorithm.implementation.TokenBucketAlgorithm;

public final class RateLimitAlgorithmFactory {

    private RateLimitAlgorithmFactory() {
    }

    public static RateLimitAlgorithm get(AlgorithmProviderType type) {
        return switch (type) {
            case FIXED_WINDOW -> FixedWindowAlgorithm.getInstance();
            case TOKEN_BUCKET -> TokenBucketAlgorithm.getInstance();
        };
    }
}
