package com.maddyjace.worldrulesmanage.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public static Message INSTANCE;

    private final Set<UUID> cooldownPlayers;
    private final long cooldownTicks;

    // 初始化构造器
    public Message(long cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
        this.cooldownPlayers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    // 发送信息
    public void sendMessage(UUID uuid, String message) {
        if (!cooldownPlayers.add(uuid)) return;
        if (message == null || message.isEmpty()) return;

        // 异步冷却队列
        Bukkit.getScheduler().runTaskLaterAsynchronously(
                Get.plugin(), () -> cooldownPlayers.remove(uuid), cooldownTicks);

        Player player = Bukkit.getPlayer(uuid);
        String str = message.trim().toLowerCase();
        String trim = str.substring(str.indexOf(':') + 1).trim();

        // 向玩家发送 title
        if (str.startsWith("title")) {
            handleTitle(player, trim.replace("&", "§"));
            return;
        }
        // 向玩家发送 actionbar
        if (str.startsWith("actionbar")) {
            sendActionBar(player, trim.replace("&", "§"));
            return;
        }
        // 向玩家发送 tell
        if (str.startsWith("tell")) {
            sendChat(player, trim.replace("&", "§"));
            return;
        }

        // 默认发送类型 actionbar
        sendActionBar(player, message.replace("&", "§"));

    }
    /** 处理 title 信息的专用方法 */
    private void handleTitle(Player player, String message) {
        String str = message.substring(message.indexOf(':') + 1).trim();
        Matcher matcher = Pattern.compile("`([^`]*)`").matcher(str);

        String title = "";
        if (matcher.find()) { title = matcher.group(1); }
        String subtitle = "";
        if (matcher.find()) { subtitle = matcher.group(1); }

        int fadeIn = 10, stay = 100, fadeOut = 10;
        String numbersPart = str.replaceAll("`[^`]*`", "").trim();
        if (!numbersPart.isEmpty()) {
            String[] nums = numbersPart.split("\\s+");
            try {
                if (nums.length >= 3) {
                    fadeIn = Integer.parseInt(nums[0]);
                    stay = Integer.parseInt(nums[1]);
                    fadeOut = Integer.parseInt(nums[2]);
                }
            } catch (NumberFormatException ignored) {}
        }
        sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
    }

    public boolean isInCooldown(UUID uuid) {
        return cooldownPlayers.contains(uuid);
    }

    public void clearCooldown(UUID uuid) {
        cooldownPlayers.remove(uuid);
    }

    public void clearAll() {
        cooldownPlayers.clear();
    }

    /**
     * 发送主标题和副标题
     *
     * @param player 目标玩家
     * @param title 主标题
     * @param subtitle 副标题，可为空
     * @param fadeIn 淡入时间（tick）
     * @param stay 停留时间（tick）
     * @param fadeOut 淡出时间（tick）
     */
    private void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(title == null ? "" : title, subtitle == null ? "" : subtitle, fadeIn, stay, fadeOut);
    }

    /**
     * 发送动作栏消息（物品栏上方）
     *
     * @param player 目标玩家
     * @param message 消息内容
     */
    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                new TextComponent(message));
    }

    /**
     * 发送聊天栏消息
     *
     * @param player 目标玩家
     * @param message 消息内容
     */
    private static void sendChat(Player player, String message) {
        player.sendMessage(message);
    }

}
