package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

// 该类当玩家扔物品时处理
public class ItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();
        if(WorldFile.INSTANCE.playerRules(world.getName(),"itemDrop", player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("ItemDropMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("ItemDropMessage"));
            }

        }

        // 指定范围触发
        Location location = player.getLocation();
        if(RadiusFile.INSTANCE.playerRules(player, world, location,"itemDrop")) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("ItemDropRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("ItemDropRadiusMessage"));
            }
        }


    }

}
