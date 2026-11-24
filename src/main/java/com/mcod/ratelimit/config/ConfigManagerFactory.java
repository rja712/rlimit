package com.mcod.ratelimit.config;

import com.mcod.ratelimit.config.implementation.DbConfigManager;
import com.mcod.ratelimit.config.implementation.HardCodedConfigManager;
import com.mcod.ratelimit.config.implementation.YamlConfigManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigManagerFactory {

    public static ConfigManager create(ConfigProviderType type) {
        return switch (type) {
            case HARDCODED -> new HardCodedConfigManager();
            case YAML -> new YamlConfigManager();
            case DB -> new DbConfigManager();
        };
    }
}
