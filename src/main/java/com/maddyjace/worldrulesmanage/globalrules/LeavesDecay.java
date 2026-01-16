package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 * 树叶消失 事件监听器
 */
public class LeavesDecay implements Listener {

    @EventHandler
    public void event(LeavesDecayEvent e) {
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置

        // 控制全局
        if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).leavesDecay) {
            e.setCancelled(true);
            return;
        }

        // 控制半径
        if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
            if (Ref.wdl.getLocalData().get(world.getName()).leavesDecay) {
                e.setCancelled(true);
            }
        }
    }

}
