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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        String itemTypeName = event.getItemDrop().getItemStack().getType().name();
        if (WorldFile.INSTANCE.playerRulesList(world.getName(), "itemDrop", itemTypeName, player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (worldFile.playerRulesMessage(world.getName(), "itemDrop") != null) {
                messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "itemDrop"));
            }
        }

        // 指定范围触发
        Location location = player.getLocation();
        if(radiusFile.playerRulesList(world, "itemDrop", itemTypeName, player, location)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (radiusFile.playerRulesMessage(world.getName(), "itemDrop") != null) {
                messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "itemDrop"));
            }
        }

    }

}
