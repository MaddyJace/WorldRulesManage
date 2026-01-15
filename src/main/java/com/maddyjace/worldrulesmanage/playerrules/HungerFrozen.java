package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.enable;
import static com.maddyjace.worldrulesmanage.util.Ref.message;

/** 防止饥饿 事件监听器 */
public class HungerFrozen implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {

        // 判断是否为玩家
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();                        // 玩家对象
        World world = player.getWorld();                               // 世界对象
        Location current = player.getLocation();                       // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).hungerFrozen;
                if (WorldUtil.conJud(map, enable)) {
                    e.setCancelled(true);
                    String message = (String) map.get(Ref.message);
                    Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                }
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getLocalData().get(world.getName()).permission;
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).hungerFrozen;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map, enable)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }

    }

}
