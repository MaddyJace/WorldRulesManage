package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

// 该类用于阻止实体对方块教程破坏
public class EntityBlockBreak implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;

    // 阻止所有实体爆炸破坏方块(苦力怕、TNT、TNT矿车等)
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        World world = event.getEntity().getWorld();
        if(worldFile.globalRules(world.getName(),"entityBlockBreak")) {
            // 清空破坏方块列表 来 阻止破坏
            event.blockList().clear();
        }

        // 指定范围触发
        Location location = event.getEntity().getLocation();
        if(radiusFile.globalRules(world,"entityBlockBreak", location)) {
            // 清空破坏方块列表 来 阻止破坏
            event.blockList().clear();
        }

    }

    // 阻止爆炸方块，破坏方块
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        World world = event.getBlock().getWorld();
        if(worldFile.globalRules(world.getName(),"entityBlockBreak")) {
            // 清空破坏方块列表 来 阻止破坏
            event.blockList().clear();
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(radiusFile.globalRules(world,"entityBlockBreak", location)) {
            // 清空破坏方块列表 来 阻止破坏
            event.blockList().clear();
        }

    }

    // 阻止实体直接改变方块
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        World world = event.getBlock().getWorld();
        if(worldFile.globalRules(world.getName(),"entityBlockBreak")) {
            event.setCancelled(true);
        }

        // 指定范围触发
        Location location = event.getBlock().getLocation();
        if(radiusFile.globalRules(world,"entityBlockBreak", location)) {
            event.setCancelled(true);
        }
    }

}
