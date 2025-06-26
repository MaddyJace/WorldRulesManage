package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

// 该类用于 阻止玩家受伤 和 击退
public class PlayerDamage implements Listener {

    // 取消受伤
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            World world = player.getWorld();
            if(WorldFile.INSTANCE.playerRules(world.getName(),"playerDamage", player)) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if(MessageFile.getMessage("PlayerDamageMessage") != null) {
                    MessageFile.parsePlaceholders(player, MessageFile.getMessage("PlayerDamageMessage"));
                }
            }

            Location location = player.getLocation();
            // 指定范围触发
            if(RadiusFile.INSTANCE.playerRules(player, world, location,"playerDamage")) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if(MessageFile.getMessage("PlayerDamageRadiusMessage") != null) {
                    MessageFile.parsePlaceholders(player, MessageFile.getMessage("PlayerDamageRadiusMessage"));
                }
            }

        }
    }

    // 取消击退
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            World world = player.getWorld();
            if(WorldFile.INSTANCE.playerRules(world.getName(),"playerDamage", player)) {
                event.setCancelled(true);
            }

            // 指定范围触发
            Location location = player.getLocation();
            if(RadiusFile.INSTANCE.playerRules(player, world, location,"playerDamage")) {
                event.setCancelled(true);
            }

        }

    }

}
