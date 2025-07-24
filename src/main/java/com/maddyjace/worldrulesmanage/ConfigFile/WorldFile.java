package com.maddyjace.worldrulesmanage.ConfigFile;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public enum WorldFile {
    INSTANCE;

    private Plugin plugin;

    // 主要缓存
    // private FileConfiguration worldFile;
    // 二级缓存
    private final Map<String, ConfigurationSection> worldConfigCache = new HashMap<>();

    // 需要在 onEnable() 初始化
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        File filePath = new File(plugin.getDataFolder(), "world.yml"); // 文件绝对路径

        FileConfiguration worldFile = YamlConfiguration.loadConfiguration(filePath);          // 加载配置文件
        worldConfigCache.clear(); // 清空旧缓存
        for (String key : worldFile.getKeys(false)) {
            ConfigurationSection section = worldFile.getConfigurationSection(key);
            if (section != null && section.contains("worldName")) {
                String worldName = section.getString("worldName");
                worldConfigCache.put(worldName, section); // 以 worldName 为索引
            }
        }

    }

    // 重载配置文件
    public void reload() {
        initialize(plugin);
    }



    /*
     * ---------------------------------------------------------------------------------------------------------------
     */



    /**
     * 该方法通过currentWorld和type形参参数查找配置文件中对应世界的配置项，是开启还是关闭。
     *
     * @param currentWorld 世界名称
     * @param type         读取类型(boolean)
     * @return             boolean
     */
    public boolean globalRules(String currentWorld,String type) {
        // 直接从缓存取出对应世界的配置
        ConfigurationSection pack = worldConfigCache.get(currentWorld);
        if (pack == null) return false;
        // 读取 globalRules 列
        ConfigurationSection global = pack.getConfigurationSection("globalRules");
        if (global == null) return false;
        return global.getBoolean(type, false);
    }

    /**
     * 该方法通过currentWorld(世界名)查找globalRules配置下的子配置表中list列表的值是否与形参listValue匹配。
     *
     * @param currentWorld 要查找的世界名
     * @param path         要查找的配置列
     * @param listValue    要查找的配置列中的list值
     * @return             boolean
     */
    public boolean globalRulesList(String currentWorld, String path, String listValue) {
        // 直接从缓存取出对应世界的配置
        ConfigurationSection pack = worldConfigCache.get(currentWorld);
        if (pack == null) return false;
        // 读取 globalRules 列
        ConfigurationSection globalRules = pack.getConfigurationSection("globalRules");
        if (globalRules == null) return false;
        // 读取 globalRules列 中的 path列
        ConfigurationSection globalRulesList = globalRules.getConfigurationSection(path);
        if (globalRulesList == null) return false;
        // 查找配置文件中的列表值是否与listValue匹配
        return findListValues(globalRulesList, listValue);
    }



    /*
     * ----------------------------------------------------------------------------------------------------------------
     */



    /**
     * 该方法通过currentWorld(世界名)查找playerRules配置下的子配置表中的enable，是开启还是关闭。
     *
     * @param playerWorld 要查找的世界名
     * @param path        要查找的配置列
     * @param player      玩家对象
     * @return            boolean
     */
    public boolean playerRules(String playerWorld, String path, Player player) {
        // 从缓存取出对应世界的配置
        ConfigurationSection pack = worldConfigCache.get(playerWorld);
        if (pack == null) return false;
        // 读取 playerRules 列
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return false;
        // 读取 playerRules -> permission
        String permission = playerRules.getString("permission");
        if(permission != null && player.hasPermission(permission)) return false;
        // 读取 playerRules -> path -> enable(value: true/false)
        return playerRules.getBoolean(path + ".enable", false);
    }

    /**
     * 该方法通过currentWorld(世界名)查找playerRules配置下的子配置表中list列表的值是否与形参listValue匹配。
     *
     * @param playerWorld 要查找的世界名
     * @param path        要查找的配置列
     * @param listValue   要查找的配置列中的list值
     * @param player      玩家对象
     * @return            boolean
     */
    public boolean playerRulesList(String playerWorld, String path, String listValue, Player player) {
        // 从缓存取出对应世界的配置
        ConfigurationSection pack = worldConfigCache.get(playerWorld);
        if (pack == null) return false;
        // 读取 playerRules 列
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return false;
        // 读取 playerRules -> permission
        String permission = playerRules.getString("permission");
        if(permission != null && player.hasPermission(permission)) return false;
        // 读取 playerRules -> path
        ConfigurationSection playerRulesList = playerRules.getConfigurationSection(path);
        if (playerRulesList == null) return false;
        return findListValues(playerRulesList, listValue);
    }

    /**
     * 该方法通过currentWorld(世界名)获取playerRules配置下的子配置表中的message，字符串。
     *
     * @param playerWorld 要查找的世界名
     * @param path        读取类型(boolean)
     * @return            String
     */
    public String playerRulesMessage(String playerWorld, String path) {
        ConfigurationSection pack = worldConfigCache.get(playerWorld);
        if (pack == null) return null;
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return null;
        if (playerRules.getString(path + ".message", null) != null) {
            return playerRules.getString(path + ".message", null);
        }
        return null;
    }



    /*
     * -------------------------------------------------------------------------------------------------------------
     */


    /**
     * 这是一个静态工具方法，用于查找 xxx -> list 中的值与value是否匹配，匹配就返回true否则就返回false。
     *
     * @param section 配置文件对象
     * @param value   要查找的配置列中的list值
     * @return        boolean
     */
    public static boolean findListValues(ConfigurationSection section, String value) {
        String rulesType = section.getString("type");
        List<String> list = section.getStringList("list");
        if ( rulesType.equalsIgnoreCase("blacklist") ) {
            if(!list.isEmpty()) {
                for (String keys : list) {
                    if (value.equalsIgnoreCase( keys.toLowerCase() )) {
                        return  true;
                    }
                }
            } return false;
        }  else if(rulesType.equalsIgnoreCase("whitelist") ) {
            if(!list.isEmpty()) {
                for (String keys : list) {
                    if (!value.equalsIgnoreCase( keys.toLowerCase() )) {
                        return true;
                    }
                }
            } else {
                return true;
            }
            return false;

        }
        return false;
    }

}
