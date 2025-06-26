package com.maddyjace.worldrulesmanage.ConfigFile;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

@SuppressWarnings("ALL")
public enum WorldFile {
    INSTANCE;

    private Plugin plugin;

    private FileConfiguration worldFile;


    // 需要在 onEnable() 初始化该 initialize 方法
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        File filePath = new File(plugin.getDataFolder(), "world.yml"); // 文件绝对路径
        worldFile = YamlConfiguration.loadConfiguration(filePath);          // 加载配置文件
    }

    // 重载配置文件
    public void reload() {
        initialize(plugin);
    }

    public boolean globalRules(String currentWorld,String type) {
        for(String radiusFileSection : worldFile.getKeys(false)) {
            ConfigurationSection section = worldFile.getConfigurationSection(radiusFileSection);
            try {
                String world = section.getString("worldName");
                ConfigurationSection global = section.getConfigurationSection("globalRules");
                if(world.equals(currentWorld) && global.getBoolean(type)) {
                    return true;
                }
            } catch (Exception e) {
                return  false;
            }
        }
        return false;           
    }


    public boolean playerRules(String currentWorld,String type,Player player) {
        for(String radiusFileSection : worldFile.getKeys(false)) {
            ConfigurationSection section = worldFile.getConfigurationSection(radiusFileSection);
            try {
                String world = section.getString("worldName");
                ConfigurationSection global = section.getConfigurationSection("playerRules");
                String permission = global.getString("permission");
                if(!hasPermission(player, permission) && world.equals(currentWorld) && global.getBoolean(type)) {
                    return true;
                }
            } catch (Exception e) {
                return  false;
            }
        }
        return false;
    }

    public boolean playerRulesList(String currentWorldName, String playerRulesType, Player player, String playerRulesTypeRulesName, String entityName) {
        for(String radiusFileSection : worldFile.getKeys(false)) {
            ConfigurationSection section = worldFile.getConfigurationSection(radiusFileSection);
            try {
                String world = section.getString("worldName");
                ConfigurationSection global = section.getConfigurationSection(playerRulesType);
                String permission = global.getString("permission");
                if(!hasPermission(player, permission) && world.equals(currentWorldName)) {
                    ConfigurationSection playerRules = global.getConfigurationSection(playerRulesTypeRulesName);
                    String rulesType = playerRules.getString("type").toLowerCase();
                    List<String> entityList = playerRules.getStringList("list");
                    if(rulesType.equalsIgnoreCase( "BLACKLIST".toLowerCase() )) {
                        for(String entity : entityList) {
                            if(entityName.equalsIgnoreCase(entity.toLowerCase())) {
                                return true;
                            }
                        }
                    } else if(rulesType.equalsIgnoreCase( "WHITELIST".toLowerCase() )) {
                        for(String entity : entityList) {
                            if(!entityName.equalsIgnoreCase( entity.toLowerCase() )) {
                                return true;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                return  false;
            }
        }
        return false;
    }

    /**
     * 该静态方法判定玩家是否有指定的权限
     */
    public static boolean hasPermission(Player player, String permission) {
        if (player == null || permission == null) return false;
        return player.hasPermission(permission);

    }

}
