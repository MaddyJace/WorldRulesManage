package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

// 该类是监听器，监听玩家破坏方块
public class BlockBreak implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = event.getBlock().getWorld();
        String blockName = event.getBlock().getType().toString();


        if(worldFile.playerRulesList(world.getName(), "blockBreak", blockName, player)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (worldFile.playerRulesMessage(world.getName(), "blockBreak") != null) {
                messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "blockBreak"));
            }
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(radiusFile.playerRulesList(world, "blockBreak", blockName, player, location)) {
            event.setCancelled(true);
            // 取消事件后向玩家发送提示信息
            if (radiusFile.playerRulesMessage(world.getName(), "blockBreak") != null) {
                messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "blockBreak"));
            }
        }


//        if(worldFile.playerRules(world.getName(),"blockBreak", player)) {
//            event.setCancelled(true);
//            // 取消事件后向玩家发送提示信息
//            if (worldFile.playerRulesMessage(world.getName(), "blockBreak") != null) {
//                messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "blockBreak"));
//            }
//        }
//
//        // 指定范围触发
//        Location location = event.getBlock().getLocation();
//        if(radiusFile.playerRules(world,"blockBreak", player, location)) {
//            event.setCancelled(true);
//            // 取消事件后向玩家发送提示信息
//            if (radiusFile.playerRulesMessage(world.getName(), "blockBreak") != null) {
//                messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "blockBreak"));
//            }
//        }

    }

}
