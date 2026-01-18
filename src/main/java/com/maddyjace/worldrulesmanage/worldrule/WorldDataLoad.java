package com.maddyjace.worldrulesmanage.worldrule;

import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum WorldDataLoad {
    INSTANCE;

    public File globalRulesFolder = WorldUtil.getWorldFolder("globalRules");
    private final Map<String, WorldRuleField> globalData = new ConcurrentHashMap<>();

    public File localRulesFolder = WorldUtil.getWorldFolder("localRules");
    private final Map<String, WorldRuleField> localData = new ConcurrentHashMap<>();

    /** 启动时逻辑 */
    public void onEnable() {
        globalData.clear();
        localData.clear();

        // 遍历 globalRules 文件夹中的所有YML
        WorldUtil.getWorldFiles(globalRulesFolder, (noExtension, file) -> {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            globalData.put(noExtension, parseYAMLData(noExtension, yaml, false));
        });

        // 遍历 localRules 文件夹中的所有YML
        WorldUtil.getWorldFiles(localRulesFolder, (noExtension, file) -> {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            localData.put(noExtension, parseYAMLData(noExtension, yaml, true));
        });

    }

    /** 关闭时逻辑 */
    public void onDisable() {
        globalData.clear();
        localData.clear();
        globalRulesFolder = null;
        localRulesFolder = null;
    }

    /** 获取 {@code globalData} 对象 */
    public Map<String, WorldRuleField> getGlobalData() {
        return globalData;
    }
    /** 获取 {@code localData} 对象 */
    public Map<String, WorldRuleField> getLocalData() {
        return localData;
    }

    /** 解析配置文件 */
    @SuppressWarnings({"ALL"})
    public WorldRuleField parseYAMLData(String noFileExtension, ConfigurationSection YAML, boolean isLocal) {
        WorldRuleField wrf = new WorldRuleField();

        String gr;
        String pr;
        // radius
        if (isLocal) {
            gr = "localRules.";
            pr = "localPlayerRules.";
            wrf.model  = YAML.getInt("radius.model", 1);  // 半径模型
            wrf.radius = YAML.getInt("radius.radius", 0); // 半径范围
            wrf.xyz    = YAML.getStringList("radius.xyz");  // 坐标位置
        } else {
            gr = "globalRules.";
            pr = "playerRules.";
        }

        // worldName
        wrf.worldName = noFileExtension;

        // globalRules
        wrf.keepInventory  = YAML.getBoolean(gr + "keepInventory", false);  // 死亡掉落
        wrf.liquidFlow     = YAML.getBoolean(gr + "liquidFlow", false);     // 液体流动
        wrf.leavesDecay    = YAML.getBoolean(gr + "leavesDecay", false);    // 树叶消失
        wrf.iceMelts       = YAML.getBoolean(gr + "iceMelts", false);       // 冰融化
        wrf.waterFreezes   = YAML.getBoolean(gr + "waterFreezes", false);   // 水结冰
        wrf.iceSpread      = YAML.getBoolean(gr + "iceSpread", false);      // 冰霜蔓延（特殊：冰霜行者）
        wrf.grassSpread    = YAML.getBoolean(gr + "grassSpread", false);    // 草地蔓延
        wrf.myceliumSpread = YAML.getBoolean(gr + "myceliumSpread", false); // 菌丝蔓延
        wrf.fireSpread     = YAML.getBoolean(gr + "fireSpread", false);     // 火焰蔓延
        if (!isLocal) {
            wrf.weatherChange  = YAML.getLong(gr + "weatherChange", -1);     // 改变天气
            wrf.timeChange     = YAML.getLong(gr + "timeChange", -1);        // 改变时间
        }
        wrf.creatureSpawn  = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(gr + "creatureSpawn.enable", false));
            put("checkMode" , YAML.getString (gr + "creatureSpawn.checkMode", "BLACKLIST"));
            put("entityList", YAML.getStringList(gr + "creatureSpawn.entityList"));
            put("animals"   , YAML.getBoolean(gr + "creatureSpawn.animals", false));
            put("monster"   , YAML.getBoolean(gr + "creatureSpawn.monster", false));
            put("ironGolem" , YAML.getBoolean(gr + "creatureSpawn.ironGolem", false));
        }};  // 实体生物
        wrf.entityExplode  = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(gr + "entityExplode.enable", false));
            put("checkMode" , YAML.getString (gr + "entityExplode.checkMode", "WHITELIST"));
            put("list", YAML.getStringList   (gr + "entityExplode.list"));
        }};     // 实体爆炸
        wrf.entityChangeBlock  = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(gr + "entityChangeBlock.enable", false));
            put("checkMode" , YAML.getString (gr + "entityChangeBlock.checkMode", "WHITELIST"));
            put("entityList", YAML.getStringList   (gr + "entityChangeBlock.entityList"));
        }}; // 实体改变方块
        wrf.blockBurn  = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(gr + "blockBurn.enable", false));
            put("checkMode" , YAML.getString (gr + "blockBurn.checkMode", "WHITELIST"));
            put("blockList", YAML.getStringList   (gr + "blockBurn.blockList"));
        }}; // 烧掉方块

        // playerRules
        wrf.permission = YAML.getString(pr + "permission", "worldrulesmanage.admin"); // 玩家权限
        wrf.lightTheBlock = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "lightTheBlock.enable", false));
            put("message", YAML.getString (pr + "lightTheBlock.message", ""));
            put("checkMode" , YAML.getString(pr + "lightTheBlock.checkMode", "WHITELIST"));
            put("blockList"  , YAML.getStringList(pr + "lightTheBlock.blockList"));
        }}; // 阻止玩家点燃方块
        wrf.usePail = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "usePail.enable", false));
            put("message", YAML.getString (pr + "usePail.message", ""));
        }}; // 阻止玩家使用桶(如: 拿空桶装 水/岩浆)
        wrf.hungerFrozen = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "hungerFrozen.enable", false));
            put("message", YAML.getString (pr + "hungerFrozen.message", ""));
        }}; // 阻止玩家饥饿
        wrf.alwaysSatiated = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "alwaysSatiated.enable", false));
            put("message", YAML.getString  (pr + "alwaysSatiated.message", ""));
            put("foodLevel", YAML.getInt(pr + "foodLevel", 20));
        }}; // 恢复玩家饥饿(等级: 20)

        wrf.customInjured = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "customInjured.enable", false));
            put("message", YAML.getString (pr + "customInjured.message", ""));
            // 自然伤害类型开关，默认关闭
            put("damageCauseEnable", YAML.getBoolean(pr + "customInjured.damageCauseEnable", false));
            put("allCause", YAML.getBoolean(pr + "customInjured.damageCause.allCause", false));
            put("contact", YAML.getBoolean(pr + "customInjured.damageCause.contact", false));
            put("entityAttack", YAML.getBoolean(pr + "customInjured.damageCause.entityAttack", false));
            put("entitySweepAttack", YAML.getBoolean(pr + "customInjured.damageCause.entitySweepAttack", false));
            put("projectile", YAML.getBoolean(pr + "customInjured.damageCause.projectile", false));
            put("suffocation", YAML.getBoolean(pr + "customInjured.damageCause.suffocation", false));
            put("fall", YAML.getBoolean(pr + "customInjured.damageCause.fall", false));
            put("fire", YAML.getBoolean(pr + "customInjured.damageCause.fire", false));
            put("fireTick", YAML.getBoolean(pr + "customInjured.damageCause.fireTick", false));
            put("lava", YAML.getBoolean(pr + "customInjured.damageCause.lava", false));
            put("drowning", YAML.getBoolean(pr + "customInjured.damageCause.drowning", false));
            put("blockExplosion", YAML.getBoolean(pr + "customInjured.damageCause.blockExplosion", false));
            put("entityExplosion", YAML.getBoolean(pr + "customInjured.damageCause.entityExplosion", false));
            put("voidDamage", YAML.getBoolean(pr + "customInjured.damageCause.voidDamage", false));
            put("lightning", YAML.getBoolean(pr + "customInjured.damageCause.lightning", false));
            put("suicide", YAML.getBoolean(pr + "customInjured.damageCause.suicide", false));
            put("starvation", YAML.getBoolean(pr + "customInjured.damageCause.starvation", false));
            put("poison", YAML.getBoolean(pr + "customInjured.damageCause.poison", false));
            put("magic", YAML.getBoolean(pr + "customInjured.damageCause.magic", false));
            put("wither", YAML.getBoolean(pr + "customInjured.damageCause.wither", false));
            put("fallingBlock", YAML.getBoolean(pr + "customInjured.damageCause.fallingBlock", false));
            put("thorns", YAML.getBoolean(pr + "customInjured.damageCause.thorns", false));
            put("dragonBreath", YAML.getBoolean(pr + "customInjured.damageCause.dragonBreath", false));
            put("custom", YAML.getBoolean(pr + "customInjured.damageCause.custom", false));
            put("flyIntoWall", YAML.getBoolean(pr + "customInjured.damageCause.flyIntoWall", false));
            put("hotFloor", YAML.getBoolean(pr + "customInjured.damageCause.hotFloor", false));
            put("cramming", YAML.getBoolean(pr + "customInjured.damageCause.cramming", false));
        }}; // 玩家受到自定义伤害

        wrf.knockbackImmunity = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "knockbackImmunity.enable", false));
            put("message", YAML.getString (pr + "knockbackImmunity.message", ""));
        }}; // 阻止玩家被伤害击退
        wrf.portal = new ConcurrentHashMap<String, Object>() {{
            put("enable" , YAML.getBoolean(pr + "portal.enable", false));
            put("message", YAML.getString(pr + "portal.message", ""));
            put("all" , YAML.getBoolean(pr + "portal.all", false));
            put("nether" , YAML.getBoolean(pr + "portal.nether", false));
            put("ender" , YAML.getBoolean(pr + "portal.ender", false));
        }}; // 阻止玩家使用传送门
        wrf.blockBreak = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "blockBreak.enable", false));
            put("message"   , YAML.getString(pr + "blockBreak.message", ""));
            put("checkMode" , YAML.getString(pr + "blockBreak.checkMode", "WHITELIST"));
            put("blockList" , YAML.getStringList(pr + "blockBreak.blockList"));
        }}; // 阻止玩家破坏方块
        wrf.blockPlace = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "blockPlace.enable", false));
            put("message"   , YAML.getString(pr + "blockPlace.message", ""));
            put("checkMode" , YAML.getString(pr + "blockPlace.checkMode", "WHITELIST"));
            put("blockList" , YAML.getStringList(pr + "blockPlace.blockList"));
        }}; // 阻止玩家放置方块
        wrf.itemPickup = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "itemPickup.enable", false));
            put("message"   , YAML.getString(pr + "itemPickup.message", ""));
            put("checkMode" , YAML.getString(pr + "itemPickup.checkMode", "WHITELIST"));
            put("itemList"  , YAML.getStringList(pr + "itemPickup.itemList"));
        }}; // 阻止玩家捡物品
        wrf.itemDrop = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "itemDrop.enable", false));
            put("message"   , YAML.getString(pr + "itemDrop.message", ""));
            put("checkMode" , YAML.getString(pr + "itemDrop.checkMode", "WHITELIST"));
            put("itemList"  , YAML.getStringList(pr + "itemDrop.itemList"));
        }}; // 阻止玩家扔物品
        wrf.useItem = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "useItem.enable", false));
            put("message"   , YAML.getString(pr + "useItem.message", ""));
            put("checkMode" , YAML.getString(pr + "useItem.checkMode", "WHITELIST"));
            put("itemList"  , YAML.getStringList(pr + "useItem.itemList"));
        }}; // 阻止玩家使用物品
        wrf.interactEntity = new ConcurrentHashMap<String, Object>() {{
            put("enable"     , YAML.getBoolean(pr + "interactEntity.enable", false));
            put("message"    , YAML.getString(pr + "interactEntity.message", ""));
            put("checkMode"  , YAML.getString(pr + "interactEntity.checkMode", "WHITELIST"));
            put("entityList" , YAML.getStringList(pr + "interactEntity.entityList"));
        }}; // 阻止玩家互动实体
        wrf.interactBlock = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "interactBlock.enable", false));
            put("message"   , YAML.getString(pr + "interactBlock.message", ""));
            put("checkMode" , YAML.getString(pr + "interactBlock.checkMode", "WHITELIST"));
            put("blockList" , YAML.getStringList(pr + "interactBlock.blockList"));
        }}; // 阻止玩家与方块互动
        wrf.triggerBlock = new ConcurrentHashMap<String, Object>() {{
            put("enable"    , YAML.getBoolean(pr + "triggerBlock.enable", false));
            put("message"   , YAML.getString(pr + "triggerBlock.message", ""));
            put("checkMode" , YAML.getString(pr + "triggerBlock.checkMode", "WHITELIST"));
            put("blockList" , YAML.getStringList(pr + "triggerBlock.blockList"));
        }}; // 阻止玩家触发方块

        return wrf;
    }

}
