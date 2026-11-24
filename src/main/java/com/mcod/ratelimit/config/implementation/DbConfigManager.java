package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.model.config.Configuration;

public class DbConfigManager implements ConfigManager {

    @Override
    public Configuration getConfiguration() {
        // TODO: read configuration from a database.
        throw new UnsupportedOperationException("DB config not implemented");
    }
}
