package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LiquidFlow implements Listener {
    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        World world = event.getBlock().getWorld();
        if(WorldFile.INSTANCE.globalRules(world.getName(),"liquidFlow")) {
            event.setCancelled(true);
        }

        Location location = event.getBlock().getLocation();
        if(RadiusFile.INSTANCE.globalRules(world, location,"liquidFlow")) {
            event.setCancelled(true);
        }

    }
}
