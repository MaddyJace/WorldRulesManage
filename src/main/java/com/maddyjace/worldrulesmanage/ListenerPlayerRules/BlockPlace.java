package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


// 该类是监听器，监听玩家放置方块
public class BlockPlace implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.playerRules(world.getName(),"blockPlace", player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("BlockPlaceMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockPlaceMessage"));
            }
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(RadiusFile.INSTANCE.playerRules(player, world, location,"blockPlace")) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("BlockPlaceRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("BlockPlaceRadiusMessage"));
            }
        }

    }

}
