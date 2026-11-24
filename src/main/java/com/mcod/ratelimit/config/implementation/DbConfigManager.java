package com.mcod.ratelimit.config.implementation;

import com.mcod.ratelimit.config.ConfigManager;
import com.mcod.ratelimit.config.model.Rule;

public class DbConfigManager implements ConfigManager {

    @Override
    public Rule getRule(String apiName) {
        // TODO: read rule from a database.
        throw new UnsupportedOperationException("DB config not implemented");
    }

    @Override
    public long getCleaningIntervalSeconds() {
        // TODO: read cleaning interval from a database.
        throw new UnsupportedOperationException("DB config not implemented");
    }
}
