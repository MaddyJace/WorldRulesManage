package com.maddyjace.worldrulesmanage.ListenerPlayerRules;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

// 取消饥饿度减少事件
public class FoodLevelChange implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            World world = player.getWorld();
            if(WorldFile.INSTANCE.playerRules(world.getName(),"food", player)) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (worldFile.playerRulesMessage(world.getName(), "food") != null) {
                    messageFile.actionBarChatMessage(player,  worldFile.playerRulesMessage(world.getName(), "food"));
                }
            }

            if(WorldFile.INSTANCE.playerRules(world.getName(),"foodSatiety", player)) {
                Bukkit.getScheduler().runTaskLater(
                        JavaPlugin.getProvidingPlugin(this.getClass()),
                        () -> player.setFoodLevel(20), 1L // 延迟1tick确保事件已处理完
                );
                // 取消事件后向玩家发送提示信息
                if (worldFile.playerRulesMessage(world.getName(), "foodSatiety") != null) {
                    messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "foodSatiety"));
                }
            }

            // 指定范围触发
            Location  location = event.getEntity().getLocation();
            if(radiusFile.playerRules(world, "food", player, location)) {
                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (radiusFile.playerRulesMessage(world.getName(), "food") != null) {
                    messageFile.actionBarChatMessage(player,  radiusFile.playerRulesMessage(world.getName(), "food"));
                }
            }

            if(radiusFile.playerRules(world, "foodSatiety", player, location)) {
                Bukkit.getScheduler().runTaskLater(
                        JavaPlugin.getProvidingPlugin(this.getClass()),
                        () -> player.setFoodLevel(20), 1L // 延迟1tick确保事件已处理完
                );
                // 取消事件后向玩家发送提示信息
                if (radiusFile.playerRulesMessage(world.getName(), "foodSatiety") != null) {
                    messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "foodSatiety"));
                }
            }

        }


    }
}
