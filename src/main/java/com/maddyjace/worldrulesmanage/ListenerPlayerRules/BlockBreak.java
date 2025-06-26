package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

// 该类是监听器，监听玩家破坏方块
public class BlockBreak implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.playerRules(world.getName(),"blockBreak", player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("BlockBreakMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockBreakMessage"));
            }
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(RadiusFile.INSTANCE.playerRules(player, world, location, "blockBreak")) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("BlockBreakRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockBreakRadiusMessage"));
            }
        }

    }

}
