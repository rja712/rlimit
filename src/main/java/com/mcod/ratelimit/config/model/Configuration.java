package com.mcod.ratelimit.config.model;

import java.util.Map;

public final class Configuration {

    private final Rule defaultRule;
    private final Map<String, Rule> rules;
    private final long cleaningIntervalSeconds;

    public Configuration(Rule defaultRule, Map<String, Rule> rules, long cleaningIntervalSeconds) {
        this.defaultRule = defaultRule;
        this.rules = Map.copyOf(rules);
        this.cleaningIntervalSeconds = cleaningIntervalSeconds;
    }

    public Rule getRule(String apiName) {
        return rules.getOrDefault(apiName, defaultRule);
    }

    public long getCleaningIntervalSeconds() {
        return cleaningIntervalSeconds;
    }
}
