package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 生成生物 事件监听器 */
public class CreatureSpawn implements Listener {

    @EventHandler
    public void event(CreatureSpawnEvent e) {
        String entityType = e.getEntityType().name(); // 实体名称
        World world = e.getLocation().getWorld();     // 世界对象
        Location current = e.getLocation();           // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).creatureSpawn;
            if (WorldUtil.conJud(map, enable, checkMode, entityList, entityType)) {
                e.setCancelled(true);
                return;
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
            Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).creatureSpawn;
            if (WorldUtil.conJud(map, enable, checkMode, entityList, entityType)) {
                e.setCancelled(true);
                return;
            }
        }


        LivingEntity entity = e.getEntity();
        // 敌对
        if (entity instanceof Monster) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("monster")) {
                    e.setCancelled(true);
                    return;
                }
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("monster")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        // 友好
        if (entity instanceof Animals) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("animals")) {
                    e.setCancelled(true);
                    return;
                }
            }
            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("animals")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        if (entity instanceof IronGolem) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
                Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("ironGolem")) {
                    e.setCancelled(true);
                    return;
                }
            }
            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
                Map<String, Object> map = Ref.wdl.getLocalData().get(world.getName()).creatureSpawn;
                if ((boolean) map.get(enable) && (boolean) map.get("ironGolem")) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
