package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;
/** 🔥火焰蔓延  事件监听器 */
public class FireSpread implements Listener {

    @EventHandler
    public void event(BlockSpreadEvent e) {
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置

        if (e.getSource().getType() == Material.FIRE) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).fireSpread) {
                e.setCancelled(true);
                return;
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
                if (Ref.wdl.getLocalData().get(world.getName()).fireSpread) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
