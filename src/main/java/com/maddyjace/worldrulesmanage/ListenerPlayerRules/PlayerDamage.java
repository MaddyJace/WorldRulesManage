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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;
    private final MessageFile messageFile = MessageFile.INSTANCE;

    // 取消受伤
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {



        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();
            World world = player.getWorld();
            if(worldFile.playerRules(world.getName(),"playerDamage", player)) {

                // 允许致命伤害，防止 /kill 等命令无法杀死玩家的问题
                // 注意: 此功能在worldFile中新增 playerRules 的功能重载用于读取！
                if(worldFile.playerRules(world.getName(),"playerDamage", "fatalInjury", player)) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE
                            || event.getCause() == EntityDamageEvent.DamageCause.CUSTOM
                            || event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                        return;
                    }
                }

                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (worldFile.playerRulesMessage(world.getName(), "playerDamage") != null) {
                    messageFile.actionBarChatMessage(player, worldFile.playerRulesMessage(world.getName(), "playerDamage"));
                }
            }

            Location location = player.getLocation();
            // 指定范围触发
            if(radiusFile.playerRules( world, "playerDamage", player, location)) {

                if(radiusFile.playerRules( world, "playerDamage", "fatalInjury", player, location)) {
                    if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE
                            || event.getCause() == EntityDamageEvent.DamageCause.CUSTOM
                            || event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()) {
                        return;
                    }
                }

                event.setCancelled(true);
                // 取消事件后向玩家发送提示信息
                if (radiusFile.playerRulesMessage(world.getName(), "playerDamage") != null) {
                    messageFile.actionBarChatMessage(player, radiusFile.playerRulesMessage(world.getName(), "playerDamage"));
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
            if(worldFile.playerRules(world.getName(),"playerDamage", player)) {
                event.setCancelled(true);
            }

            // 指定范围触发
            Location location = player.getLocation();
            if(radiusFile.playerRules(world, "playerDamage", player, location)) {
                event.setCancelled(true);
            }

        }

    }

}
