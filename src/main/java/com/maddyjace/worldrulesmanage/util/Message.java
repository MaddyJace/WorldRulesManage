package com.maddyjace.worldrulesmanage.util;

import me.clip.placeholderapi.PlaceholderAPI;
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
        if (message.isEmpty()) return;
        message = message.replace("&", "§");

        // 异步冷却队列
        if (!cooldownPlayers.add(uuid)) return;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Get.plugin(), () -> cooldownPlayers.remove(uuid), cooldownTicks);

        Player player = Bukkit.getPlayer(uuid);

        String[] result = parsePrefix(message);
        if (result == null || result.length != 2) {
            // 默认发送类型 actionbar
            sendActionBar(player, message);
            return;
        }
        switch (result[0].toUpperCase()) {
            case "TELL"      : sendChat      (player, parse(player, result[1])); return;
            case "TITLE"     : sendTitle     (player, parse(player, result[1])); return;
            case "ACTIONBAR" : sendActionBar (player, parse(player, result[1])); return;
            default          : sendActionBar(player, message);
        }

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


    /** 发送主标题和副标题 */
    private static void sendTitle(Player player, String input) {
        if (player == null || input == null || input.trim().isEmpty()) return;

        String title, subTitle = "";
        int fadeIn = 10, stay = 100, fadeOut = 10;

        Pattern pattern = Pattern.compile(
                "`([^`]*)`" +                         // title
                "(?:\\s+`([^`]*)`)?" +                // optional subtitle
                "(?:\\s+(\\d+)\\s+(\\d+)\\s+(\\d+))?" // optional numbers
        );
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            title = matcher.group(1);
            if (matcher.group(2) != null) {
                subTitle = matcher.group(2);
            }
            if (matcher.group(3) != null) {
                try { fadeIn = Integer.parseInt(matcher.group(3)); } catch (Exception ignored) {}
                try { stay   = Integer.parseInt(matcher.group(4)); } catch (Exception ignored) {}
                try { fadeOut= Integer.parseInt(matcher.group(5)); } catch (Exception ignored) {}
            }
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        }
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


    // ----------------------------------- //

    /**
     * 拆分输入的字符串前缀是否命中(tell、title、actionbar)命中解析为数组并转小写，<p>
     * 第0个元素是前缀，第1个元素是后缀。
     *
     * @param input 要解析的字符串，例如：tell: Hello
     * @return 长度为 2 的数组，index 0 为前缀，index 1 为后缀
     */
    private static String[] parsePrefix(String input) {
        if (input == null) return null;

        Pattern pattern = Pattern.compile("^(tell|title|actionbar):\\s*(.+)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String prefix = matcher.group(1).toLowerCase(); // 前缀统一小写
            String content = matcher.group(2);             // 命令内容
            return new String[]{prefix, content};
        }

        return null; // 不符合格式
    }

    /** 通过 PlaceholderAPI 解析字符串占位符  */
    private static String parse(Player player, String srt) {
        try {
            return PlaceholderAPI.setPlaceholders(player, srt);
        } catch (Exception e) {
            return srt;
        }
    }

}
