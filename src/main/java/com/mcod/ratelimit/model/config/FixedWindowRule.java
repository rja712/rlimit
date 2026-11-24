package com.mcod.ratelimit.model.config;

import lombok.Value;

@Value
public class FixedWindowRule implements Rule {
    int requestsPerSecond;
    FallbackPolicy fallbackPolicy;
}
