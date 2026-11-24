package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.Rule;

public class YamlConfigManager implements ConfigManager {

    @Override
    public Rule getRule(String apiName) {
        // TODO: read rule from a YAML file.
        throw new UnsupportedOperationException("YAML config not implemented");
    }
}
