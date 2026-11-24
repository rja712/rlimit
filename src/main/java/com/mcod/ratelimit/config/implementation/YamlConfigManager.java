package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.model.config.Configuration;

public class YamlConfigManager implements ConfigManager {

    @Override
    public Configuration getConfiguration() {
        // TODO: read configuration from a YAML file.
        throw new UnsupportedOperationException("YAML config not implemented");
    }
}
