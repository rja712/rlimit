package com.mcod.ratelimit.config;

import com.mcod.ratelimit.model.config.Rule;

public interface ConfigManager {

    Rule getRule(String apiName);
}
