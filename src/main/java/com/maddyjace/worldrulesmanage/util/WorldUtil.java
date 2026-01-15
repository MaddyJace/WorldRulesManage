package com.maddyjace.worldrulesmanage.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class WorldUtil {

    /** 检查 {@code ./plugins/WorldRulesManage} 目录中是否有XXX文件夹没有就创建并初始化！ */
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public static File getWorldFolder(String folderName) {
        File pluginFolder = Get.plugin().getDataFolder();
        if (!pluginFolder.isDirectory()) {
            throw new IllegalStateException();
        }
        File taskFolder = new File(pluginFolder, folderName);
        if (!taskFolder.exists()) {
            taskFolder.mkdirs();
            if (folderName.equals("globalRules")) {
                Get.plugin().saveResource("globalRules/world.yml", false);
                Get.plugin().saveResource("globalRules/world_nether.yml", false);
                Get.plugin().saveResource("globalRules/world_the_end.yml", false);
                if (Language.getServerLanguage().equalsIgnoreCase("zh_cn")) {
                    Get.plugin().saveResource("globalRules/zh_CN/world.yml", false);
                    Get.plugin().saveResource("globalRules/zh_CN/world_nether.yml", false);
                    Get.plugin().saveResource("globalRules/zh_CN/world_the_end.yml", false);
                }
            } else if (folderName.equals("localRules")) {
                Get.plugin().saveResource("localRules/world.yml", false);
                Get.plugin().saveResource("localRules/world_nether.yml", false);
                Get.plugin().saveResource("localRules/world_the_end.yml", false);
                if (Language.getServerLanguage().equalsIgnoreCase("zh_cn")) {
                    Get.plugin().saveResource("localRules/zh_CN/world.yml", false);
                    Get.plugin().saveResource("localRules/zh_CN/world_nether.yml", false);
                    Get.plugin().saveResource("localRules/zh_CN/world_the_end.yml", false);
                }
            }
        }
        return taskFolder;
    }

    /**
     * 读取指定文件夹中的全部{@code XXX.YAML}文件，并单独执行逻辑。<p>
     * 注意：该方法只查找根目录文件夹，不会递归查找子文件夹中的{@code .YAML}
     */
    public static void getWorldFiles(File folder, BiConsumer<String, File> runnable) {
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (!file.isFile()) continue;
            if (!WorldUtil.isYamlFile(file.getName())) continue;

            String noFileExtension = WorldUtil.removeSuffix(file.getName());
            if (noFileExtension == null) continue;

            runnable.accept(noFileExtension, file);
        }
    }
    /** 忽略大小写判断文件名后缀是否为 .YAML */
    private static boolean isYamlFile(String name) {
        if (name == null) return false;
        if (name.toLowerCase(Locale.ROOT).endsWith(".yml")) {
            return true;
        } else return name.toLowerCase(Locale.ROOT).endsWith(".yaml");
    }
    /** 删除文件后缀保留文件名 */
    public static String removeSuffix(String name) {
        if (name.toLowerCase(Locale.ROOT).endsWith(".yml")) {
            return name.substring(0, name.length() - 4);
        } else if (name.toLowerCase(Locale.ROOT).endsWith(".yaml")) {
            return name.substring(0, name.length() - 5);
        } return null;
    }

    /**
     * 此方法适用判断 checkMode 功能！
     * @param value     目标名称
     * @param checkMode 模式类型
     * @param list      查找列表
     * @return          如果目标名称从列表查找到找到就返回true否则false
     */
    public static boolean findList(String value, String checkMode, List<String> list) {
        /*
            当list为空表时(这个list是配置项的)
            如果 checkMode 的模式是 WHITELIST 就返回 true
            否则 checkMode 的模式是 BLACKLIST 就返回 false
         */
        if (list == null || list.isEmpty()) {
            return checkMode.equalsIgnoreCase("WHITELIST");
        }

        // 将list表元素统一转小写
        Set<String> lowerSet = list.stream().filter(Objects::nonNull)
                .map(s -> s.toLowerCase(Locale.ROOT)).collect(Collectors.toSet());

        /*
            检查checkMode模式是否为BLACKLIST或者WHITELIST，
                如果都不是返回false，并且查找value是否在list中命中，命中就根据模式返回true或false。
         */
        String lowerValue = value.toLowerCase(Locale.ROOT);
        if (checkMode.equalsIgnoreCase("BLACKLIST")) {
            // 黑名单命中 → true
            return lowerSet.contains(lowerValue);
        } else if (checkMode.equalsIgnoreCase("WHITELIST")) {
            // 白名单不命中 → false
            return !lowerSet.contains(lowerValue);
        } else {
            // 未知模式，默认 false
            return false;
        }
    }

    /**
     * 判断玩家是否在规则内
     * 在判断是否要取消事件时需要写大量的判断语句，因此会存在很多冗余代码， <p>
     * 此模板只需要传入相应参数即可判断，根据引查看使用示例。
     */
    public static boolean conJud(Map<String, Object> map, String enable, String checkMode, String list, String targetName) {
        if ((boolean) map.get(enable)) {
            String str = (String) map.get(checkMode);
            @SuppressWarnings("unchecked")
            List<String> lists = (List<String>) map.get(list);
            return WorldUtil.findList(targetName, str, lists);
        }
        return false;
    }
    public static boolean conJud(Map<String, Object> map, String enable) {
        return (boolean) map.get(enable);
    }

    /** 判断玩家是否在半径内 */
    public static boolean inRadius(World world, Location current) {
        int model = Ref.wdl.getLocalData().get(world.getName()).model;
        int radius = Ref.wdl.getLocalData().get(world.getName()).radius;
        List<String> xyz = Ref.wdl.getLocalData().get(world.getName()).xyz;
        return WorldUtil.inRadius(world, current, model, radius, xyz);
    }
    private static boolean inRadius(World world, Location current, int model, int radius, List<String> list) {
        for (String str : list) {

            // 将坐标位置按空格或连续空格拆分为数组(严格判断数组length)
            String[] parts = str.trim().split("\\s+");
            if (parts.length != 3) return false;

            // 尝试通过parts数组获取Bukkit的Location对象，失败返回false
            Location center;
            int[] xyz = new int[3];
            try { xyz[0] = Integer.parseInt(parts[0]);
                  xyz[1] = Integer.parseInt(parts[1]);
                  xyz[2] = Integer.parseInt(parts[2]);
                  center = new Location(world, xyz[0], xyz[1], xyz[2]);
            } catch (NumberFormatException e) { return false; }

            /*
                0表示没有模式，因为globalRules中没有radius，需要给一个默认值来跳过。
                根据半径（model）模式进入分支，并判断current和radius坐标是否在radius半径内。
             */
            switch (model) {
                case 0: return false;
                case 1: if (isIn2DRange(center, current, radius)) return true;
                case 2: if (isIn3DRange(center, current, radius)) return true;
                case 3: if (isIn2DCube(center, current, radius))  return true;
                case 4: if (isIn3DCube(center, current, radius))  return true;
            }
        }
        return false;
    }
    /** 二维圆：计算center(中心)坐标和current(目标)坐标是否在radius(半径)内！ */
    private static boolean isIn2DRange(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;
        double dx = current.getX() - center.getX();
        double dz = current.getZ() - center.getZ();
        return dx * dx + dz * dz <= radius * radius;
    }
    /** 三维圆：计算center(中心)坐标和current(目标)坐标是否在radius(半径)内！ */
    private static boolean isIn3DRange(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;
        double dx = current.getX() - center.getX();
        double dy = current.getY() - center.getY();
        double dz = current.getZ() - center.getZ();
        return dx * dx + dy * dy + dz * dz <= radius * radius;
    }

    /** 二维方：计算center(中心)坐标和current(目标)坐标是否在radius(半径)内！ */
    private static boolean isIn2DCube(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;
        double dx = Math.abs(current.getX() - center.getX());
        double dz = Math.abs(current.getZ() - center.getZ());
        return dx <= radius && dz <= radius;
    }
    /** 三维方：计算center(中心)坐标和current(目标)坐标是否在radius(半径)内！ */
    private static boolean isIn3DCube(Location center, Location current, int radius) {
        if (center == null || current == null) return false;
        if (!center.getWorld().equals(current.getWorld())) return false;
        double dx = Math.abs(current.getX() - center.getX());
        double dy = Math.abs(current.getY() - center.getY());
        double dz = Math.abs(current.getZ() - center.getZ());
        return dx <= radius && dy <= radius && dz <= radius;
    }

}
