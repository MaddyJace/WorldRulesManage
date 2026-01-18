package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

/** 🍄 菌丝蔓延 事件监听器 */
public class MyceliumSpread implements Listener {

    @EventHandler
    public void event(BlockSpreadEvent e) {
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置

        if (e.getSource().getType() == getMycelium()) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).myceliumSpread) {
                e.setCancelled(true);
                return;
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName())) {
                if (Ref.wdl.getLocalData().get(world.getName()).myceliumSpread) {
                    if (WorldUtil.inRadius(world, current)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    /** 兼容在 1.8-1.12.x 和 1.13+ 对 MYCELIUM 不同的命名！*/
    private static Material getMycelium() {
        try {
            // 1.13+
            return Material.valueOf("MYCELIUM");
        } catch (IllegalArgumentException e) {
            // 1.8 - 1.12.x
            return Material.MYCEL;
        }
    }

}
