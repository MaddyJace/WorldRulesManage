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

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {

        World world = event.getBlock().getWorld();
        if(worldFile.globalRules(world.getName(),"leavesDecay")) {
            event.setCancelled(true);
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(radiusFile.globalRules(world,"leavesDecay", location)) {
            event.setCancelled(true);
        }

    }

}
