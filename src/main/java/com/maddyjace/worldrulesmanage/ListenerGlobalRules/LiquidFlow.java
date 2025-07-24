package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LiquidFlow implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        World world = event.getBlock().getWorld();
        if(worldFile.globalRules(world.getName(),"liquidFlow")) {
            event.setCancelled(true);
        }

        Location location = event.getBlock().getLocation();
        if(radiusFile.globalRules(world,"liquidFlow", location)) {
            event.setCancelled(true);
        }

    }
}
