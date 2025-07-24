package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Entity entity = event.getRightClicked();
        String entityName = entity.getType().toString().toLowerCase();

        if(worldFile.playerRulesList(world.getName(), "playerInteractEntity", entityName, player)) {
            event.setCancelled(true);
        }

        // 指定范围触发
        Location location = entity.getLocation();
        if(radiusFile.playerRulesList(world, "playerInteractEntity", entityName, player, location)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Entity entity = event.getRightClicked();
        String entityName = entity.getType().toString().toLowerCase();

        if(worldFile.playerRulesList(world.getName(), "playerInteractEntity", entityName, player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (worldFile.playerRulesMessage(world.getName(), "playerInteractEntity") != null) {
                messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "playerInteractEntity"));
            }
        }

        // 指定范围触发
        Location location = entity.getLocation();
        if(radiusFile.playerRulesList(world, "playerInteractEntity", entityName, player, location)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (radiusFile.playerRulesMessage(world.getName(), "playerInteractEntity") != null) {
                messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "playerInteractEntity"));
            }
        }

    }


}
