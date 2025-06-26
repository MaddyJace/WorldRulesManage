package com.maddyjace.worldrulesmanage;

import com.maddyjace.worldrulesmanage.Commands.Commands;
import com.maddyjace.worldrulesmanage.ConfigFile.*;
import com.maddyjace.worldrulesmanage.ListenerGlobalRules.EntityBlockBreak;
import com.maddyjace.worldrulesmanage.ListenerGlobalRules.FlameSpread;
import com.maddyjace.worldrulesmanage.ListenerGlobalRules.LeavesDecay;
import com.maddyjace.worldrulesmanage.ListenerGlobalRules.LiquidFlow;
import com.maddyjace.worldrulesmanage.ListenerPlayerRules.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WorldRulesManage extends JavaPlugin {

    private FileWatcher watcher;

    @Override
    public void onEnable() {
        // 载入 config.yml world.yml message.yml 文件
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
            saveResource("zh-cn_config.yml", false);
        }
        File worldFile = new File(getDataFolder(), "world.yml");
        if (!worldFile.exists()) {
            saveResource("world.yml", false);
            saveResource("zh-cn_world.yml", false);
        }
        File radiusFile = new File(getDataFolder(), "radius.yml");
        if (!radiusFile.exists()) {
            saveResource("radius.yml", false);
            saveResource("zh-cn_radius.yml", false);
        }
        File messageFile = new File(getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            saveResource("message.yml", false);
            saveResource("zh-cn_message.yml", false);
        }

        // 初始化 FileWatcher 类
        watcher = new FileWatcher(getDataFolder().getAbsolutePath(), ".yml", 100);
        // 初始化 config.yml 类
        ConfigFile.INSTANCE.initialize(this);
        // 初始化 radius.yml 类
        RadiusFile.INSTANCE.initialize(this);
        // 初始化 world.yml 类
        WorldFile.INSTANCE.initialize(this);
        // 初始化 message.yml 类
        MessageFile.INSTANCE.initialize(this, Bukkit.getPluginManager().getPlugin("PlaceholderAPI"));

        // 当 config.yml 和 autoReload 为 true 时
        if(ConfigFile.getConfig("autoReload")) {
            // 开启文件监听
            try {
                watcher.start();
            } catch (Exception e) {
                getLogger().warning("The automatic reload function failed to enable!");
            }
        }

        // 注册 点燃方块 监听器
        getServer().getPluginManager().registerEvents(new BlockIgnite(), this);
        // 注册 火焰传播 监听器
        getServer().getPluginManager().registerEvents(new FlameSpread(), this);
        // 注册 树叶自然衰减 监听器
        getServer().getPluginManager().registerEvents(new LeavesDecay(), this);
        // 注册 方块被破坏 监听器
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        // 注册 方块被放置 监听器
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        // 注册 捡起物品 监听器
        getServer().getPluginManager().registerEvents(new ItemPickup(), this);
        // 注册 丢物品 监听器
        getServer().getPluginManager().registerEvents(new ItemDrop(), this);
        // 注册 使用桶 监听器
        getServer().getPluginManager().registerEvents(new PlayerBucketEmpty(), this);
        // 注册 饥饿度 监听器
        getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);
        // 注册 玩家受伤 监听器
        getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
        // 注册 玩家传送 监听器
        getServer().getPluginManager().registerEvents(new PlayerPortal(), this);
        // 注册 使用物品 监听器
        getServer().getPluginManager().registerEvents(new PlayerUseItem(), this);
        // 注册 右键实体交互 监听器
        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), this);
        // 注册 实体破坏方块 监听器
        getServer().getPluginManager().registerEvents(new EntityBlockBreak(), this);
        // 注册 液体流动 监听器
        getServer().getPluginManager().registerEvents(new LiquidFlow(), this);

        // 注册 命令 和 Tab键 监听器
        Commands commandHandler = new Commands();
        this.getCommand("worldmanage").setExecutor(commandHandler);     // 命令
        this.getCommand("worldmanage").setTabCompleter(commandHandler); // Tab

        Bukkit.getConsoleSender().sendMessage("§b§l");
        Bukkit.getConsoleSender().sendMessage("§b§l");
        Bukkit.getConsoleSender().sendMessage("§b§l _       __           __    ____  ___                           ");
        Bukkit.getConsoleSender().sendMessage("§b§l| |     / /___  _____/ /___/ /  |/  /___ _____  ____ _____ ____ ");
        Bukkit.getConsoleSender().sendMessage("§b§l| | /| / / __ \\/ ___/ / __  / /|_/ / __ `/ __ \\/ __ `/ __ `/ _ \\");
        Bukkit.getConsoleSender().sendMessage("§b§l| |/ |/ / /_/ / /  / / /_/ / /  / / /_/ / / / / /_/ / /_/ /  __/");
        Bukkit.getConsoleSender().sendMessage("§b§l|__/|__/\\____/_/  /_/\\__,_/_/  /_/\\__,_/_/ /_/\\__,_/\\__, /\\___/ ");
        Bukkit.getConsoleSender().sendMessage("§b§l                                                   /____/       ");
        Bukkit.getConsoleSender().sendMessage("§b§l");
        Bukkit.getConsoleSender().sendMessage("§7Author: §aMaddyJace §7FeedbackEmail: §edixiaomai@qq.com §7FeedbackQQ: §e2743063754 §7Version: §e1.0");
        Bukkit.getConsoleSender().sendMessage("§f-------------------------------------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("§b§l");
        Bukkit.getConsoleSender().sendMessage("§b§l");

    }

    @Override
    public void onDisable() {
        // 关闭文件监听
        try {
            watcher.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
