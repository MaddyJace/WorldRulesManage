package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import static com.maddyjace.worldrulesmanage.util.Ref.*;
import java.util.Map;

/** 实体/方块爆炸 事件监听器*/
public class EntityExplode implements Listener {

    @EventHandler
    public void entityExplode(EntityExplodeEvent e) {
        World world = e.getEntity().getWorld();             // 世界对象
        String entityName = e.getEntity().getType().name(); // 实体名称
        Location current = e.getEntity().getLocation();     // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).entityExplode;
            if (WorldUtil.conJud(map, enable, checkMode, list, entityName)) {
                e.blockList().clear();
                return;
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            Map<String, Object> creatureSpawn = Ref.wdl.getLocalData().get(world.getName()).entityExplode;
            if (WorldUtil.conJud(creatureSpawn, enable, checkMode, list, entityName)) {
                if (WorldUtil.inRadius(world, current)) {
                    e.blockList().clear();
                }
            }
        }

    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent e) {
        World world = e.getBlock().getWorld();            // 世界对象
        Location current = e.getBlock().getLocation();    // 坐标位置
        String blockName = e.getBlock().getType().name(); // 实体名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).entityExplode;
            if (WorldUtil.conJud(map, enable, checkMode, list, blockName)) {
                e.blockList().clear();
                return;
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            Map<String, Object> creatureSpawn = Ref.wdl.getLocalData().get(world.getName()).entityExplode;
            if (WorldUtil.conJud(creatureSpawn, enable, checkMode, list, blockName)) {
                if (WorldUtil.inRadius(world, current)) {
                    e.blockList().clear();
                }
            }
        }
    }

}
