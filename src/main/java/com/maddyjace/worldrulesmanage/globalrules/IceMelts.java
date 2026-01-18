package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/** 🧊冰融化 事件监听器 */
public class IceMelts implements Listener {

	@EventHandler
	public void event(BlockFadeEvent e) {
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置

        if (e.getBlock().getType() == Material.ICE) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).iceMelts) {
                e.setCancelled(true);
                return;
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName())) {
                if (Ref.wdl.getLocalData().get(world.getName()).iceMelts) {
                    if (WorldUtil.inRadius(world, current)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
