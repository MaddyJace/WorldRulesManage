package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

// 该类监听玩家捡起物品
public class ItemPickup implements Listener {


    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {

        Player player = (Player) event.getEntity();
        World world = player.getWorld();
        if(WorldFile.INSTANCE.playerRules(world.getName(),"itemPickup", player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("ItemPickupMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("ItemPickupMessage"));
            }
        }

        // 指定范围触发
        Location location = player.getLocation();
        if(RadiusFile.INSTANCE.playerRules(player, world, location,"itemPickup")) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("ItemPickupRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("ItemPickupRadiusMessage"));
            }
        }
    }
}
