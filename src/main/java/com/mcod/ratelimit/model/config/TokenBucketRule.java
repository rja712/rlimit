package com.mcod.ratelimit.model.config;

import lombok.Value;

@Value
public class TokenBucketRule implements Rule {
    int maxTokens;
    int refillRate;
    FallbackPolicy fallbackPolicy;
}
