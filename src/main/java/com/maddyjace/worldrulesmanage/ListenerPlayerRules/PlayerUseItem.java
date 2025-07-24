package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

// 该类用于阻止玩家使用物品
public class PlayerUseItem implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    // 当玩家使用物品时处理
    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event) {

        Player player = event.getPlayer(); // 获取玩家类型
        World world = player.getWorld();   // 获取玩家所在世界类型
        ItemStack item = event.getItem();  // 获取物品堆叠
        if(item != null) { // 该对象不能是空的
            String itemName = String.valueOf(item.getType()); // 获取物品常量名称
            if(WorldFile.INSTANCE.playerRulesList(world.getName(),"playerUseItem", itemName, player)) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (worldFile.playerRulesMessage(world.getName(), "playerUseItem") != null) {
                    messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "playerUseItem"));
                }
            }

            // 指定范围触发
            Location location = player.getLocation();
            if(radiusFile.playerRulesList(world, "playerUseItem", itemName, player, location)) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (radiusFile.playerRulesMessage(world.getName(), "playerUseItem") != null) {
                    messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "playerUseItem"));
                }
            }

        }

    }

    // 当玩家与方块交互时
    @EventHandler
    public void onPlayerUseItem01(PlayerInteractEvent event) {

        // 当玩家是右键点击时处理
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // 右键点击的方块不能为空(如: 空气)
            if (event.getClickedBlock() != null) {
                Player player = event.getPlayer(); // 获取玩家类型
                World world = player.getWorld();   // 获取玩家所在世界类型
                String blockName = event.getClickedBlock().getType().toString(); // 获取玩家右键点击的方块常量名称
                if(worldFile.playerRulesList(world.getName(), "playerInteractBlock", blockName, player)) {
                    event.setCancelled(true);
                    // 取消事件后向玩家发送提示信息
                    if (worldFile.playerRulesMessage(world.getName(), "playerInteractBlock") != null) {
                        messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "playerInteractBlock"));
                    }
                }

                // 指定范围触发
                Location location = event.getClickedBlock().getLocation();
                if(radiusFile.playerRulesList(world, "playerInteractBlock", blockName, player, location)) {
                    event.setCancelled(true);
                    // 取消事件后向玩家发送提示信息
                    if (radiusFile.playerRulesMessage(world.getName(), "playerInteractBlock") != null) {
                        messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "playerInteractBlock"));
                    }
                }

            }
        }

    }

    // 该方法当玩家触发方块时处理
    @EventHandler
    public void onPlayerUseItem02(PlayerInteractEvent event) {

        if (event.getAction() == Action.PHYSICAL) {
            Player player = event.getPlayer(); // 获取玩家类型
            World world = player.getWorld();   // 获取玩家所在世界类型
            String blockName = event.getClickedBlock() != null ? event.getClickedBlock().getType().toString() : null;
            if(blockName != null) {
                if(worldFile.playerRulesList(world.getName(), "playerTriggerBlock", blockName, player)) {
                    event.setCancelled(true);
                    // 取消事件后向玩家发送提示信息
                    if (worldFile.playerRulesMessage(world.getName(), "playerTriggerBlock") != null) {
                        messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "playerTriggerBlock"));
                    }
                }

                // 指定范围触发
                Location location = player.getLocation();
                if(radiusFile.playerRulesList(world, "playerTriggerBlock", blockName, player, location)) {
                    event.setCancelled(true);
                    // 取消事件后向玩家发送提示信息
                    if (radiusFile.playerRulesMessage(world.getName(), "playerTriggerBlock") != null) {
                        messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "playerTriggerBlock"));
                    }
                }
            }
        }

    }

}
