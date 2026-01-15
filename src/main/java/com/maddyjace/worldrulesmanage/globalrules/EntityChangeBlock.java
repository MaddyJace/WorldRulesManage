package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import static com.maddyjace.worldrulesmanage.util.Ref.*;

import java.util.Map;

/** 实体改变方块 事件监听器 */
public class EntityChangeBlock implements Listener {

    @EventHandler
    public void event(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Player) return;

        World world = e.getBlock().getWorld();             // 世界对象
        Location current = e.getBlock().getLocation();     // 坐标位置
        String entityName = e.getBlock().getType().name(); // 方块名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).entityChangeBlock;
            if (WorldUtil.conJud(map, enable, checkMode, entityList, entityName)) {
                e.setCancelled(true);
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
            Map<String, Object> creatureSpawn = Ref.wdl.getLocalData().get(world.getName()).entityChangeBlock;
            if (WorldUtil.conJud(creatureSpawn, enable, checkMode, entityList, entityName)) {
                e.setCancelled(true);
            }
        }
    }

}
