package com.mcod.ratelimit.config;

import com.mcod.ratelimit.config.model.Rule;

public interface ConfigManager {

    Rule getRule(String apiName);
}
