package com.maddyjace.worldrulesmanage.ConfigFile;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public enum ConfigFile {
    INSTANCE;

    private Plugin plugin;
    private YamlConfiguration configFile;

    // 需要在 onEnable() 初始化该 initialize 方法
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        File filePath = new File(plugin.getDataFolder(), "config.yml"); // 文件绝对路径
        configFile = YamlConfiguration.loadConfiguration(filePath);          // 加载配置文件
    }

    // 重载配置文件
    public void reload() {
        initialize(plugin);
    }

    public YamlConfiguration getConfigFile() {
        return configFile;
    }

    // 读取配置文件
    public static boolean getConfig(String config) {
        return ConfigFile.INSTANCE.getConfigFile().getBoolean(config);
    }

}
