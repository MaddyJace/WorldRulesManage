package com.maddyjace.worldrulesmanage.playerrules;

import com.maddyjace.worldrulesmanage.util.Message;
import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 捡起物品 监听器 */
public class ItemPickup implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        // 判断是否为玩家
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();                        // 玩家对象
        World world = player.getWorld();                               // 世界对象
        Location current = player.getLocation();                       // 坐标位置
        String itemName = e.getItem().getItemStack().getType().name(); // 物品名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getGlobalData().get(world.getName()).permission;
            // 当没有权限时
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).itemPickup;
                if (WorldUtil.conJud(map, enable, checkMode, itemList, itemName)) {
                    e.setCancelled(true);
                    String message = (String) map.get(Ref.message);
                    Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    return;
                }
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            String permission = Ref.wdl.getLocalData().get(world.getName()).permission;
            if (!(player.hasPermission(permission))) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).itemPickup;
                if (WorldUtil.conJud(map, enable, checkMode, itemList, itemName)) {
                    if (WorldUtil.inRadius(world, current)) {
                        e.setCancelled(true);
                        String message = (String) map.get(Ref.message);
                        Message.INSTANCE.sendMessage(player.getUniqueId(), message);
                    }
                }
            }
        }
    }

}
