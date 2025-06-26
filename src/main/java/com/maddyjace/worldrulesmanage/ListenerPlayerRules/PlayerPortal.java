package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;


// 该类用于阻止进入传送门
public class PlayerPortal implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {

        Player player = event.getPlayer(); // 玩家名称
        World world = player.getWorld();   // 玩家所在世界名称
        // 当 portalAll: true 时阻止所有传送
        if(WorldFile.INSTANCE.playerRules(world.getName(),"portalAll", player)) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalAllMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalAllMessage"));
            }

            return;
        }

        Block block = player.getLocation().getBlock(); // 玩家所在位置的方块
        Material type = block.getType();               // 所在位置的方块类型

        // 当 portalAll: false 时阻止特定传送
        boolean portalNether = WorldFile.INSTANCE.playerRules(world.getName(),"portalNether", player);
        if(type == Material.PORTAL && portalNether) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalNetherMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalNetherMessage"));
            }

        }

        boolean portalEnder = WorldFile.INSTANCE.playerRules(world.getName(), "portalEnder", player);
        if(type == Material.ENDER_PORTAL && portalEnder) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalEnderMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalEnderMessage"));
            }


        }

    }

    // 指定范围触发
    @EventHandler
    public void onPlayerPortalRadius(PlayerPortalEvent event) {

        Player player = event.getPlayer(); // 玩家名称
        World world = player.getWorld();   // 玩家所在世界名称
        Location location = player.getLocation();
        // 当 portalAll: true 时阻止所有传送
        if(RadiusFile.INSTANCE.playerRules(player, world, location,"portalAll")) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalAllRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalAllRadiusMessage"));
            }

            return;
        }

        Block block = player.getLocation().getBlock(); // 玩家所在位置的方块
        Material type = block.getType();               // 所在位置的方块类型

        // 当 portalAll: false 时阻止特定传送
        boolean portalNether = RadiusFile.INSTANCE.playerRules(player, world, location,"portalNether");
        if(type == Material.PORTAL && portalNether) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalNetherRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalNetherRadiusMessage"));
            }
        }

        boolean portalEnder = RadiusFile.INSTANCE.playerRules(player, world, location, "portalEnder");
        if(type == Material.ENDER_PORTAL && portalEnder) {
            event.setCancelled(true);
            pushAwayPlayer(player);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PortalEnderRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PortalEnderRadiusMessage"));
            }


        }

    }

    // 向玩家当前面朝的相反方向推开
    private void pushAwayPlayer(Player player) {
        Vector backward = player.getLocation().getDirection().multiply(-0.5).setY(0.5);
        player.setVelocity(backward);
    }



}
