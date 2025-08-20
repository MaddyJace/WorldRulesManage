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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {

        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        World world = player.getWorld();
        String itemTypeName = event.getItem().getItemStack().getType().name();
        if (WorldFile.INSTANCE.playerRulesList(world.getName(), "itemPickup", itemTypeName, player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (worldFile.playerRulesMessage(world.getName(), "itemPickup") != null) {
                messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "itemPickup"));
            }
        }

        // 指定范围触发
        Location location = player.getLocation();
        if(radiusFile.playerRulesList(world, "itemPickup", itemTypeName, player, location)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (radiusFile.playerRulesMessage(world.getName(), "itemPickup") != null) {
                messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "itemPickup"));
            }
        }
    }

}
