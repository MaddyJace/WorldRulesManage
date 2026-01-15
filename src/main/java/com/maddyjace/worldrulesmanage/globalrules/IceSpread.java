package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Ref;
import com.maddyjace.worldrulesmanage.util.WorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

/** ❄️ 冰霜蔓延（特殊：冰霜行者） 事件监听器 */
public class IceSpread implements Listener {

    @EventHandler
    public void event(BlockFormEvent e){
        World world = e.getBlock().getWorld();         // 世界对象
        Location current = e.getBlock().getLocation(); // 坐标位置

        if (e.getNewState().getType() != null || e.getNewState().getType() == Material.FROSTED_ICE) {
            // 控制全局
            if (Ref.wdl.getGlobalData().containsKey(world.getName()) && Ref.wdl.getGlobalData().get(world.getName()).iceSpread) {
                e.setCancelled(true);
            }

            // 控制半径
            if (Ref.wdl.getLocalData().containsKey(world.getName()) && WorldUtil.inRadius(world, current)) {
                if (Ref.wdl.getLocalData().get(world.getName()).iceSpread) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
