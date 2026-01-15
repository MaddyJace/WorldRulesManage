package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 禁止用桶 */
public class UsePail implements Listener {

    // 阻止所有"xxx_BUCKET"类型的桶
    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent e) {
        Material bucket = e.getBucket(); // 物品对象

        if (bucket.name().endsWith("_BUCKET")) {
            Player player = e.getPlayer();           // 玩家对象
            World world = player.getWorld();         // 世界对象
            Location current = player.getLocation(); // 坐标位置

            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
                String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
                // 当没有权限时
                if (!(player.hasPermission(permission))) {
                    Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).usePail;
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
                    Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).usePail;
                    if (WorldUtil.inRadius(world, current)) {
                        if (WorldUtil.conJud(map,enable)) {
                            e.setCancelled(true);
                            String message = (String) map.get(Ref.message);
                            Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                        }
                    }
                }
            }
        }

    }

    // 防止玩家获得液体
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {

        Player player = e.getPlayer();           // 玩家对象
        World world = player.getWorld();         // 世界对象
        Location current = player.getLocation(); // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).usePail;
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
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).usePail;
                if (WorldUtil.inRadius(world, current)) {
                    if (WorldUtil.conJud(map,enable)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }

    }

}
