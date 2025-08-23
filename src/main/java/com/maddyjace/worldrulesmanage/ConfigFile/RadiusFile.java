package com.maddyjace.worldrulesmanage.ConfigFile;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 配置文件的核心逻辑
 */

@SuppressWarnings("ALL")
public enum RadiusFile {
    INSTANCE;

    private Plugin plugin;

    private final Map<String, ConfigurationSection> radiusConfigCache = new HashMap<>();


    // 需要在 onEnable() 初始化该 initialize 方法
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        File filePath = new File(plugin.getDataFolder(), "radius.yml"); // 文件绝对路径

        FileConfiguration radiusFile = YamlConfiguration.loadConfiguration(filePath);
        radiusConfigCache.clear(); // 清空旧缓存
        for (String key : radiusFile.getKeys(false)) {
            ConfigurationSection section = radiusFile.getConfigurationSection(key);
            if (section != null && section.contains("worldName")) {
                String worldName = section.getString("worldName");
                radiusConfigCache.put(worldName, section); // 以 worldName 为索引
            }
        }

    }

    // 重载配置文件
    public void reload() {
        initialize(plugin);
    }


    /*
     * ----------------------------------------------------------------------------------------------------------------
     */


    /**
     * 该方法通过currentWorld和type形参参数查找配置文件中对应世界的配置项，是开启还是关闭。
     *
     * @param currentWorld 世界名称
     * @param type         读取类型(boolean)
     * @return             boolean
     */
    public boolean globalRules(World currentWorld, String type, Location goalLocation) {
        // 直接从缓存取出对应世界的配置
        ConfigurationSection pack = radiusConfigCache.get(currentWorld.getName());
        if (pack == null) return false;
        // 读取 globalRules 列
        ConfigurationSection global = pack.getConfigurationSection("globalRules");
        if (global == null) return false;
        // 获取到YML的 settingsRadius 下的键
        ConfigurationSection settingsRadius = pack.getConfigurationSection("settingsRadius");
        if(settingsRadius == null) return false;
        if (settingsRadius(settingsRadius, currentWorld, goalLocation)) {
            return global.getBoolean(type, false);
        }
        return false;
    }


    /**
     * 该方法通过currentWorld(世界名)查找globalRules配置下的子配置表中list列表的值是否与形参listValue匹配。
     *
     * @param currentWorld 要查找的世界名
     * @param path         要查找的配置列
     * @param listValue    要查找的配置列中的list值
     * @return             boolean
     */
    public boolean globalRulesList(World currentWorld, String path, String listValue, Location goalLocation) {
        // 直接从缓存取出对应世界的配置
        ConfigurationSection pack = radiusConfigCache.get(currentWorld.getName());
        if (pack == null) return false;
        // 读取 globalRules 列
        ConfigurationSection globalRules = pack.getConfigurationSection("globalRules");
        if (globalRules == null) return false;
        // 获取到YML的 settingsRadius 下的键
        ConfigurationSection settingsRadius = pack.getConfigurationSection("settingsRadius");
        if(settingsRadius == null) return false;
        // 读取 globalRules列 中的 path列
        ConfigurationSection globalRulesList = globalRules.getConfigurationSection(path);
        if (globalRulesList == null) return false;
        if (settingsRadius(settingsRadius, currentWorld, goalLocation)) {
            // 查找配置文件中的列表值是否与listValue匹配
            return WorldFile.findListValues(globalRulesList, listValue);
        }
        return false;
    }



    /*
     * ---------------------------------------------------------------------------------------------------------------
     */


    /**
     * 该方法通过currentWorld(世界名)查找playerRules配置下的子配置表中的enable，是开启还是关闭。
     *
     * @param playerWorld 要查找的世界名
     * @param path        要查找的配置列
     * @param player      玩家对象
     * @return            boolean
     */
    public boolean playerRules(World playerWorld, String path, Player player,Location goalLocation) {
        // 从缓存取出对应世界的配置
        ConfigurationSection pack = radiusConfigCache.get(playerWorld.getName());
        if (pack == null) return false;
        // 读取 playerRules 列
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return false;
        // 读取 playerRules -> permission
        String permission = playerRules.getString("permission");
        if(permission != null && player.hasPermission(permission)) return false;
        // 获取到YML的 settingsRadius 下的键
        ConfigurationSection settingsRadius = pack.getConfigurationSection("settingsRadius");
        if(settingsRadius == null) return false;
        if (settingsRadius(settingsRadius, playerWorld, goalLocation)) {
            // 读取 playerRules -> path -> enable(value: true/false)
            return playerRules.getBoolean(path + ".enable", false);
        }
        return false;
    }

    /**
     * 该方法通过currentWorld(世界名)查找playerRules配置下的子配置表中的enable，是开启还是关闭。
     * <p> - 如：playerRules(固定) > playerDamage(对应path1) > FatalInjury(对应path2)。
     *
     * @param playerWorld 要查找的世界名
     * @param path        要查找的配置列
     * @param player      玩家对象
     * @return            boolean
     */
    public boolean playerRules(World playerWorld, String path1, String path2, Player player,Location goalLocation) {
        // 从缓存取出对应世界的配置
        ConfigurationSection pack = radiusConfigCache.get(playerWorld.getName());
        if (pack == null) return false;
        // 读取 playerRules 列
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return false;
        // 读取 playerRules -> permission
        String permission = playerRules.getString("permission");
        if(permission != null && player.hasPermission(permission)) return false;
        // 获取到YML的 settingsRadius 下的键
        ConfigurationSection settingsRadius = pack.getConfigurationSection("settingsRadius");
        if(settingsRadius == null) return false;
        if (settingsRadius(settingsRadius, playerWorld, goalLocation)) {
            // 读取 playerRules -> path -> enable(value: true/false)
            return playerRules.getBoolean(path1 + "." + path2, false);
        }
        return false;
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
    public boolean playerRulesList(World playerWorld, String path, String listValue, Player player, Location goalLocation) {
        // 从缓存取出对应世界的配置
        ConfigurationSection pack = radiusConfigCache.get(playerWorld.getName());
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
        // 获取到YML的 settingsRadius 下的键
        ConfigurationSection settingsRadius = pack.getConfigurationSection("settingsRadius");
        if(settingsRadius == null) return false;
        if (settingsRadius(settingsRadius, playerWorld, goalLocation)) {
            // 查找配置文件中的列表值是否与listValue匹配
            return WorldFile.findListValues(playerRulesList, listValue);
        }
        return false;
    }


    /**
     * 该方法通过currentWorld(世界名)获取playerRules配置下的子配置表中的message，字符串。
     *
     * @param playerWorld 要查找的世界名
     * @param path        读取类型(boolean)
     * @return            String
     */
    public String playerRulesMessage(String playerWorld, String path) {
        ConfigurationSection pack = radiusConfigCache.get(playerWorld);
        if (pack == null) return null;
        ConfigurationSection playerRules = pack.getConfigurationSection("playerRules");
        if (playerRules == null) return null;
        if (playerRules.getString(path + ".message", null) != null) {
            return playerRules.getString(path + ".message", null);
        }
        return null;
    }



    /*
     * ----------------------------------------------------------------------------------------------------------------
     */



    public boolean settingsRadius(ConfigurationSection settingsRadius, World currentWorld, Location currentLocation) {
        List<String> list = settingsRadius.getStringList("xyz");

        for(String locationRadius : list) {
            // 起点坐标
            Location startingLocation = null;
            // 获取到YML的 settingsRadius 键下 xyz 值 并拆分保存为数组
            String[] parts = locationRadius.trim().split("\\s+");
            int x; int y; int z;
            if (parts.length == 3) {
                try {
                    x = Integer.parseInt(parts[0]);
                    y = Integer.parseInt(parts[1]);
                    z = Integer.parseInt(parts[2]);
                    startingLocation = new Location(currentWorld, x, y, z);
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            // 该对象不能为空
            if(startingLocation != null) {
                // 取YML的type值
                String type = settingsRadius.getString("type").toLowerCase();
                int radius = settingsRadius.getInt("radius");
                switch (type) {
                    case "2drange": // 二维圆
                        if(isIn2DRange(currentLocation, startingLocation, radius)) {
                            return true;
                        }
                    case "3drange": // 三维圆
                        if(isIn3DRange(currentLocation, startingLocation, radius)) {
                            return true;
                        }
                    case "2dcube":  // 二维立方
                        if(isIn2DCube(currentLocation, startingLocation, radius)) {
                            return true;
                        }
                    case "3dcube":  // 三维立方
                        if(isIn3DCube(currentLocation, startingLocation, radius)) {
                            return true;
                        }
                }
            }
        }

        return false;
    }


    // 二维圆
    public boolean isIn2DRange(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;

        double dx = current.getX() - center.getX();
        double dz = current.getZ() - center.getZ();

        return dx * dx + dz * dz <= radius * radius;
    }

    // 三维圆
    public boolean isIn3DRange(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;

        double dx = current.getX() - center.getX();
        double dy = current.getY() - center.getY();
        double dz = current.getZ() - center.getZ();

        return dx * dx + dy * dy + dz * dz <= radius * radius;
    }

    // 二维方
    public boolean isIn2DCube(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;

        double dx = Math.abs(current.getX() - center.getX());
        double dz = Math.abs(current.getZ() - center.getZ());

        return dx <= radius && dz <= radius;
    }

    // 三维方
    public boolean isIn3DCube(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;

        double dx = Math.abs(current.getX() - center.getX());
        double dy = Math.abs(current.getY() - center.getY());
        double dz = Math.abs(current.getZ() - center.getZ());

        return dx <= radius && dy <= radius && dz <= radius;
    }

}
