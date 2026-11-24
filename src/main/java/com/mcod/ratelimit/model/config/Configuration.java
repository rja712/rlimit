package com.mcod.ratelimit.model.config;

import lombok.Value;

import java.util.Map;

@Value
public class Configuration {

    Rule defaultRule;
    Map<String, Rule> rules;

    public Rule getRule(String apiName) {
        return rules.getOrDefault(apiName, defaultRule);
    }
}
