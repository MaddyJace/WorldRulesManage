package com.maddyjace.worldrulesmanage.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Config {
    INSTANCE;

    // language
    public String language = "en_US";

    // message
    public int infoPeriod = 5;

    // autoReload
    public boolean autoReloadEnable = true;
    public int autoReloadPeriod = 5;

    // debug
    public boolean entityExplodeName = false;            // 实体爆炸
    public boolean blockExplodeName = false;             // 方块爆炸
    public boolean entityChangeName = false;             // 方块改变
    public boolean blockBurnName = false;                // 方块燃烧
    public boolean creatureSpawnName = false;            // 实体生成
    public boolean blockBreakName = false;               // 方块破坏
    public boolean blockPlaceName = false;               // 方块放置
    public boolean entityPickupItemName = false;         // 物品拿起
    public boolean playerDropItemName = false;           // 物品丢弃
    public boolean playerInteractRightClickName = false; // 方块交互
    public boolean playerPhysicalInteractName = false;   // 方块触发
    public boolean blockIgniteName = false;              // 方块点燃
    public boolean playerUseItemName = false;            // 物品使用
    public boolean playerInteractEntityName = false;     // 实体交互
    public boolean entityDamageName = false;             // 受伤类型

    // listener
    public boolean blockBurn = true;         // 方块燃烧
    public boolean creatureSpawn = true;     // 生成生物
    public boolean entityChangeBlock = true; // 实体改变方块
    public boolean entityExplode = true;     // 实体/方块爆炸
    public boolean fireSpread = true;        // 火焰蔓延
    public boolean grassSpread = true;       // 草地蔓延
    public boolean iceMelts = true;          // 冰融化
    public boolean iceSpread = true;         // 冰霜蔓延（特殊：冰霜行者）
    public boolean leavesDecay = true;       // 树叶消失
    public boolean liquidFlow = true;        // 液体流动
    public boolean myceliumSpread = true;    // 菌丝蔓延
    public boolean waterFreezes = true;      // 水结冰
    public boolean weatherChange = true;     // 天气变化
    public boolean timeChange = true;        // 时间变化
    public boolean alwaysSatiated = true;    // 恢复玩家饥饿值
    public boolean blockBreak = true;        // 玩家方块破坏
    public boolean blockPlace = true;        // 玩家放置方块
    public boolean customInjured = true;     // 玩家受伤
    public boolean hungerFrozen = true;      // 防止饥饿
    public boolean interactBlock = true;     // 方块交互
    public boolean interactEntity = true;    // 实体交互
    public boolean itemDrop = true;          // 扔出物品
    public boolean itemPickup = true;        // 捡起物品
    public boolean knockbackImmunity = true; // 防止击退玩家
    public boolean lightTheBlock = true;     // 点燃方块
    public boolean portal = true;            // 进传送门
    public boolean triggerBlock = true;      // 方块触发
    public boolean useItem = true;           // 使用物品
    public boolean usePail = true;           // 禁止用桶
    public boolean keepInventory = true;     // 死亡掉落
    
    public void onEnable() {
        File file = new File(Get.plugin().getDataFolder(), "config.yml");
        if (!file.exists()) {
            Get.plugin().saveResource("config.yml", false);
            if (Language.getServerLanguage().equalsIgnoreCase("zh_cn")) {
                Get.plugin().saveResource("config_zh_CN.yml", false);
            }
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // language
        language = config.getString("language");

        // message
        infoPeriod = config.getInt("infoPeriod", 5);

        // autoReload
        autoReloadEnable = config.getBoolean("autoReload.enable", true);
        autoReloadPeriod = config.getInt("autoReload.period", 5);

        // debug
        entityExplodeName = config.getBoolean("debug.entityExplodeName", false);
        blockExplodeName = config.getBoolean("debug.blockExplodeName", false);
        entityChangeName = config.getBoolean("debug.entityChangeName", false);
        blockBurnName = config.getBoolean("debug.blockBurnName", false);
        creatureSpawnName = config.getBoolean("debug.creatureSpawnName", false);
        blockBreakName = config.getBoolean("debug.blockBreakName", false);
        blockPlaceName = config.getBoolean("debug.blockPlaceName", false);
        entityPickupItemName = config.getBoolean("debug.entityPickupItemName", false);
        playerDropItemName = config.getBoolean("debug.playerDropItemName", false);
        playerInteractRightClickName = config.getBoolean("debug.playerInteractRightClickName", false);
        playerPhysicalInteractName = config.getBoolean("debug.playerPhysicalInteractName", false);
        blockIgniteName = config.getBoolean("debug.blockIgniteName", false);
        playerUseItemName = config.getBoolean("debug.playerUseItemName", false);
        playerInteractEntityName = config.getBoolean("debug.playerInteractEntityName", false);
        entityDamageName = config.getBoolean("debug.entityDamageName", false);

        // listener
        blockBurn = config.getBoolean("listener.blockBurn", true);
        creatureSpawn = config.getBoolean("listener.creatureSpawn", true);
        entityChangeBlock = config.getBoolean("listener.entityChangeBlock", true);
        entityExplode = config.getBoolean("listener.entityExplode", true);
        fireSpread = config.getBoolean("listener.fireSpread", true);
        grassSpread = config.getBoolean("listener.grassSpread", true);
        iceMelts = config.getBoolean("listener.iceMelts", true);
        iceSpread = config.getBoolean("listener.iceSpread", true);
        leavesDecay = config.getBoolean("listener.leavesDecay", true);
        liquidFlow = config.getBoolean("listener.liquidFlow", true);
        myceliumSpread = config.getBoolean("listener.myceliumSpread", true);
        waterFreezes = config.getBoolean("listener.waterFreezes", true);
        weatherChange = config.getBoolean("listener.weatherChange", true);
        timeChange = config.getBoolean("listener.timeChange", true);
        alwaysSatiated = config.getBoolean("listener.alwaysSatiated", true);
        blockBreak = config.getBoolean("listener.blockBreak", true);
        blockPlace = config.getBoolean("listener.blockPlace", true);
        customInjured = config.getBoolean("listener.customInjured", true);
        hungerFrozen = config.getBoolean("listener.hungerFrozen", true);
        interactBlock = config.getBoolean("listener.interactBlock", true);
        interactEntity = config.getBoolean("listener.interactEntity", true);
        itemDrop = config.getBoolean("listener.itemDrop", true);
        itemPickup = config.getBoolean("listener.itemPickup", true);
        knockbackImmunity = config.getBoolean("listener.knockbackImmunity", true);
        lightTheBlock = config.getBoolean("listener.lightTheBlock", true);
        portal = config.getBoolean("listener.portal", true);
        triggerBlock = config.getBoolean("listener.triggerBlock", true);
        useItem = config.getBoolean("listener.useItem", true);
        usePail = config.getBoolean("listener.usePail", true);
        keepInventory = config.getBoolean("listener.keepInventory", true);
    }

    public void onDisable() {
        onEnable();
    }
}
