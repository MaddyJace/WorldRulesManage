package com.maddyjace.worldrulesmanage.ConfigFile;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public enum MessageFile {
    INSTANCE;

    private Plugin plugin;
    private Plugin placeholderAPI;
    private YamlConfiguration messageFile;

    // 需要在 onEnable() 初始化该 initialize 方法
    public void initialize(Plugin plugin, Plugin placeholderAPI) {
        this.plugin = plugin;
        this.placeholderAPI = placeholderAPI;
        File filePath = new File(plugin.getDataFolder(), "message.yml"); // 文件绝对路径
        messageFile = YamlConfiguration.loadConfiguration(filePath);          // 加载配置文件
    }

    // 重载配置文件
    public void reload() {
        initialize(plugin, placeholderAPI);
    }

    // 设置颜色
    public static String setColors(String message) {
        return message.replace("&", "§");
    }

    // 获取 messageFile 对象
    public YamlConfiguration getMessageFile() {
        return messageFile;
    }

    // 读取配置文件
    public static String getMessage(String message) {
        if(message != null) {
            return MessageFile.INSTANCE.getMessageFile().getString(message);
        }
        return null;
    }

    // 检查PlaceholderAPI是否已安装
    public boolean isPlaceholderAPILoaded() {
        return placeholderAPI != null;
    }
    // 解析PlaceholderAPI的占位符
    public static void parsePlaceholders(Player player, String input) {
        if(input != null) {
            if(MessageFile.INSTANCE.isPlaceholderAPILoaded()) {
                String parsedPaPi = PlaceholderAPI.setPlaceholders(player, input);   // 解析PAPI占位符
                String parsedColor = parsedPaPi.replace("&", "§"); // 解析原版颜色
                MessageFile.sendMessage(player, parsedColor); // 发送信息给玩家
            } else {
                String parsedColor = input.replace("&", "§");
                MessageFile.sendMessage(player, parsedColor); // 发送信息给玩家
            }
        }
    }


    // 向控制台发送信息
    public static void CmdReloadInfo() {
        if(MessageFile.getMessage("PluginsName") != null && MessageFile.getMessage("Reload") != null) {
            Bukkit.getConsoleSender().sendMessage( "[WorldManage] " + MessageFile.setColors(MessageFile.getMessage("Reload")));
        }

    }

    // 发送信息
    public static void sendMessage(Player player, String message) {
        if((MessageFile.getMessage("PluginsName") != null)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                    MessageFile.setColors(MessageFile.getMessage("PluginsName")) +  message));
        }
    }

}
