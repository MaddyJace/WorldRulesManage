package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

//  防止树叶自然衰减/消失
public class LeavesDecay implements Listener {

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {

        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.globalRules(world.getName(),"leavesDecay")) {
            event.setCancelled(true);
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(RadiusFile.INSTANCE.globalRules(world, location,"leavesDecay")) {
            event.setCancelled(true);
        }

    }

}
