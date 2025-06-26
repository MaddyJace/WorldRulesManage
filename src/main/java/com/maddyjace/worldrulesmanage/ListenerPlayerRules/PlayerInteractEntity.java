package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.ConfigFile;
import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

// 该类阻止玩家与实体交互
public class PlayerInteractEntity implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Entity entity = event.getRightClicked();
        String entityName = entity.getType().toString().toLowerCase();

        if(WorldFile.INSTANCE.playerRulesList(world.getName(), "playerRules", player,
                "PlayerInteractEntity", entityName)) {
            event.setCancelled(true);
        }

        Location location = entity.getLocation();
        if(RadiusFile.INSTANCE.playerRulesList(player, world, location, "PlayerInteractEntity", entityName)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Entity entity = event.getRightClicked();
        String entityName = entity.getType().toString().toLowerCase();

        if(WorldFile.INSTANCE.playerRulesList(world.getName(), "playerRules", player,
                "PlayerInteractEntity", entityName)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PlayerInteractEntityMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PlayerInteractEntityMessage"));
            }
        }

        // 指定范围触发
        Location location = entity.getLocation();
        if(RadiusFile.INSTANCE.playerRulesList(player, world, location,"PlayerInteractEntity", entityName)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if(MessageFile.getMessage("PlayerInteractEntityRadiusMessage") != null) {
                MessageFile.parsePlaceholders(player, MessageFile.getMessage("PlayerInteractEntityRadiusMessage"));
            }
        }


        // 调试模式: 向控制台返回被交互的实体名称
        if(ConfigFile.getConfig("getEntityName") && MessageFile.getMessage("getEntityNameMessage") != null && player.isOp()) {
            Bukkit.getConsoleSender().sendMessage(MessageFile.setColors(MessageFile.getMessage("getEntityNameMessage")) + entityName);
        }

    }


}
