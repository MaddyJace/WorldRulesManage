package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

// 该类监听实体点燃方块
public class BlockIgnite implements Listener {

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.globalRules(world.getName(),"blockIgnite")) {
            if(player != null && !WorldFile.INSTANCE.playerRules(world.getName(),"blockIgnite", player)) { return; }
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(player != null && MessageFile.getMessage("BlockIgniteMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockIgniteMessage"));
            }

        }

    }

    // 指定范围触发
    @EventHandler
    public void onBlockIgniteRadius(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        Location location = event.getBlock().getLocation();
        if(RadiusFile.INSTANCE.globalRules(world, location, "blockIgnite")) {
            if(player != null && !RadiusFile.INSTANCE.playerRules(player, world, location,"blockIgnite")) {
                return;
            }
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if( player != null && MessageFile.getMessage("BlockIgniteRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockIgniteRadiusMessage"));
            }

        }

    }

}
