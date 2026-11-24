package com.mcod.ratelimit.model.config;

import java.util.Map;
import lombok.Value;

@Value
public class Configuration {

    Rule defaultRule;
    Map<String, Rule> rules;

    public Rule getRule(String apiName) {
        return rules.getOrDefault(apiName, defaultRule);
    }
}
