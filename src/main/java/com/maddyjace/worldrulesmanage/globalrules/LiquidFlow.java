package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * 液体流动 事件监听器
 */
public class LiquidFlow implements Listener {

    @EventHandler
    public void event(BlockFromToEvent e) {
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置
        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).liquidFlow) {
            e.setCancelled(true);
            return;
        }
        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName())) {
            if (Ref.wdl.getLocalData().get(world.getName()).liquidFlow) {
                if (WorldUtil.inRadius(world, current)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
