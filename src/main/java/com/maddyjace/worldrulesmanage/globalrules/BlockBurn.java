package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;

import java.util.Map;

import static com.maddyjace.worldrulesmanage.util.Ref.*;

/** 方块燃烧 事件监听器 */
public class BlockBurn implements Listener {

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        World world = e.getBlock().getWorld();            // 世界对象
        Location current = e.getBlock().getLocation();    // 坐标位置
        String blockName = e.getBlock().getType().name(); // 方块名称

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            Map<String, Object> map = Ref.wdl.getGlobalData().get(world.getName()).blockBurn;
            if (WorldUtil.conJud(map, enable, checkMode, blockList, blockName)) {
                e.setCancelled(true);
                return;
            }
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            Map<String, Object> creatureSpawn = Ref.wdl.getLocalData().get(world.getName()).blockBurn;
            if (WorldUtil.conJud(creatureSpawn, enable, checkMode, blockList, blockName)) {
                if (WorldUtil.inRadius(world, current)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
