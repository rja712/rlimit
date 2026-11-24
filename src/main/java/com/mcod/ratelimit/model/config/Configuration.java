package com.mcod.ratelimit.model.config;

import java.util.Map;

public final class Configuration {

    private final Rule defaultRule;
    private final Map<String, Rule> rules;

    public Configuration(Rule defaultRule, Map<String, Rule> rules) {
        this.defaultRule = defaultRule;
        this.rules = Map.copyOf(rules);
    }

    public Rule getRule(String apiName) {
        return rules.getOrDefault(apiName, defaultRule);
    }
}
