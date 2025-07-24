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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.globalRules(world.getName(),"blockIgnite")) {
            if(player != null && !WorldFile.INSTANCE.playerRules(world.getName(),"blockIgnite", player)) { return; }
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (worldFile.playerRulesMessage(world.getName(), "blockIgnite") != null) {
                if (player != null) {
                    messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "blockIgnite"));
                }
            }
        }
    }


    // 指定范围触发
    @EventHandler
    public void onBlockIgniteRadius(BlockIgniteEvent event) {

        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        Location location = event.getBlock().getLocation();
        if(radiusFile.globalRules(world, "blockIgnite", location)) {
            if(player != null && !radiusFile.playerRules(world,"blockIgnite", player, location)) {
                return;
            }
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (radiusFile.playerRulesMessage(world.getName(), "blockIgnite") != null) {
                if (player != null) {
                    messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "blockIgnite"));
                }
            }

        }

    }

}
