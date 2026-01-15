package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 实体交互 监听器 */
public class InteractEntity implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();                  // 玩家对象
        World world = player.getWorld();                // 世界对象
        Entity entity = e.getRightClicked();            // 实体对象
        Location current = entity.getLocation();        // 坐标位置
        String entityName = entity.getType().name();    // 实体名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).interactEntity;
                if (WorldUtil.conJud(map,enable, checkMode, entityList, entityName)) {
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
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).interactEntity;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map,enable, checkMode, entityList, entityName)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }

    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {

        Player player = e.getPlayer();                  // 玩家对象
        World world = player.getWorld();                // 世界对象
        Entity entity = e.getRightClicked();            // 实体对象
        Location current = entity.getLocation();        // 坐标位置
        String entityName = entity.getType().name();    // 实体名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).interactEntity;
                if (WorldUtil.conJud(map,enable, checkMode, entityList, entityName)) {
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
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).interactEntity;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map,enable, checkMode, entityList, entityName)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }

    }


}
