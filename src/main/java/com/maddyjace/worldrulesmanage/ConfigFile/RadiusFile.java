package com.maddyjace.worldrulesmanage.ConfigFile;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

@SuppressWarnings("ALL")
public enum RadiusFile {
    INSTANCE;

    private Plugin plugin;

    private FileConfiguration RadiusFile;


    // 需要在 onEnable() 初始化该 initialize 方法
    public void initialize(Plugin plugin) {
        this.plugin = plugin;
        File filePath = new File(plugin.getDataFolder(), "radius.yml"); // 文件绝对路径
        RadiusFile = YamlConfiguration.loadConfiguration(filePath);          // 加载配置文件
    }

    // 重载配置文件
    public void reload() {
        initialize(plugin);
    }


    // 接收 World Type Location
    public boolean globalRules(World goalWorld, Location goalLocation, String yamlValue) {
        // 循环遍历YML键(全部)
        for(String radiusFileSection : RadiusFile.getKeys(false)) {
            // 获取到YML的 radiusFileSection 键
            ConfigurationSection section = RadiusFile.getConfigurationSection(radiusFileSection);
            try {
                String yamlWorldName = section.getString("worldName");  // 配置的世界
                String goalWorldName = goalWorld.getName();          // 目标的世界
                // 获取到YML的 globalRules 下的键
                ConfigurationSection globalRules = section.getConfigurationSection("globalRules");
                // 获取到YML的 settingsRadius 下的键
                ConfigurationSection settingsRadius = section.getConfigurationSection("settingsRadius");

                // 判定目标所在世界是否与YML配置的世界匹配
                if(yamlWorldName.equals(goalWorldName)) {
                    // 判定目标是否在指定半径内
                    if(settingsRadius(settingsRadius, goalWorld, goalLocation)) {
                        // 判断YML的 globalRules 组的 yamlValue 类型是否开启(true)
                        if(globalRules.getBoolean(yamlValue)) {
                            return true;
                        }
                    }

                }

            } catch (Exception e) {
                return  false;
            }
        }
        return false;
    }


    public boolean playerRules(Player player, World playerWorld, Location goalLocation, String yamlValue) {
        // 循环遍历YML键(全部)
        for(String radiusFileSection : RadiusFile.getKeys(false)) {
            // 获取到YML的 radiusFileSection 键
            ConfigurationSection section = RadiusFile.getConfigurationSection(radiusFileSection);
            try {
                String yamlWorldName = section.getString("worldName"); // 配置的世界
                String playerWorldName = player.getWorld().getName();           // 玩家的世界
                // 获取到YML的 playerRules 下的键
                ConfigurationSection playerRules = section.getConfigurationSection("playerRules");
                String playerPermission = playerRules.getString("permission"); // 玩家的权限
                // 获取到YML的 settingsRadius 下的键
                ConfigurationSection settingsRadius = section.getConfigurationSection("settingsRadius");

                // 判定玩家是否拥有权限 && 判定玩家所在世界是否与YML配置的世界匹配
                if(!hasPermission(player, playerPermission) && yamlWorldName.equals(playerWorldName)) {
                    // 判定玩家是否在指定半径内 && 判断YML的 playerRules 组的 yamlValue 类型是否开启(true)
                    if(settingsRadius(settingsRadius, playerWorld, goalLocation) && playerRules.getBoolean(yamlValue)) {
                        return true;
                    }
                }

            } catch (Exception e) {
                return  false;
            }
        }
        return false;
    }

    public boolean playerRulesList(Player player, World playerWorld, Location goalLocation, String yamlValue, String goalConstantName) {
        // 循环遍历YML键(全部)
        for(String radiusFileSection : RadiusFile.getKeys(false)) {
            // 获取到YML的 radiusFileSection 键
            ConfigurationSection section = RadiusFile.getConfigurationSection(radiusFileSection);
            try {
                String yamlWorldName = section.getString("worldName"); // 配置的世界
                String playerWorldName = player.getWorld().getName();     // 玩家的世界
                // 获取到YML的 playerRules 下的键
                ConfigurationSection playerRules = section.getConfigurationSection("playerRules");
                String playerPermission = playerRules.getString("permission"); // 玩家的权限
                // 获取到YML的 settingsRadius 下的键
                ConfigurationSection settingsRadius = section.getConfigurationSection("settingsRadius");

                if(!hasPermission(player, playerPermission) && settingsRadius(settingsRadius, playerWorld, goalLocation) && yamlWorldName.equals(playerWorldName)) {
                    // 获取YAML的 playerRules 的子配置
                    ConfigurationSection sonYamlSection = playerRules.getConfigurationSection(yamlValue);
                    // 获取YAML的 playerRules -> sonYamlSection -> type 的值
                    String rulesType = sonYamlSection.getString("type").toLowerCase();
                    // 获取YAML的 playerRules -> sonYamlSection -> list 的列表
                    List<String> list = sonYamlSection.getStringList("list");
                    // 判断 rulesType 是否为 BLACKLIST 不是在判断是否为 WHITELIST 在不是就继续处理
                    if(rulesType.equalsIgnoreCase( "BLACKLIST".toLowerCase() )) {
                        // 循环遍历list
                        for(String constantName : list) {
                            // 判断 constantName 与 goalConstantName 是否匹配
                            if(goalConstantName.equalsIgnoreCase( constantName.toLowerCase() )) {
                                return true;
                            }
                        }
                    } else if(rulesType.equalsIgnoreCase( "WHITELIST".toLowerCase() )) {
                        // 循环遍历list
                        for(String constantName : list) {
                            // 判断 constantName 与 goalConstantName 是否匹配，取反
                            if(!goalConstantName.equalsIgnoreCase( constantName.toLowerCase() )) {
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



    public boolean settingsRadius(ConfigurationSection settingsRadius, World currentWorld, Location currentLocation) {

        // 起点坐标
        Location startingLocation = null;
        // 获取到YML的 settingsRadius 键下 xyz 值 并拆分保存为数组
        String[] parts = settingsRadius.getString("xyz").trim().split("\\s+");
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
                    return isIn2DRange(currentLocation, startingLocation, radius);
                case "3drange": // 三维圆
                    return isIn3DRange(currentLocation, startingLocation, radius);
                case "2dcube":  // 二维立方
                    return isIn2DCube(currentLocation, startingLocation, radius);
                case "3dcube":  // 三维立方
                    return isIn3DCube(currentLocation, startingLocation, radius);
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

    public static boolean hasPermission(Player player, String permission) {
        if (player == null || permission == null) return false;
        return player.hasPermission(permission);

    }

}
