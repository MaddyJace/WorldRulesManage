package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.RadiusFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

// 监听实体生成
public class CreatureSpawn implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;
    private final RadiusFile radiusFile = RadiusFile.INSTANCE;

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {

        // 获取生物的实体类型名称
        EntityType entityType = event.getEntityType();
        String entityName = entityType.name();

        // 获取生物所在的世界对象
        World world = event.getLocation().getWorld();
        if(worldFile.globalRulesList(world.getName(), "creatureSpawn", entityName)) {
            event.setCancelled(true);
        }

        // 指定范围触发
        Location location = event.getLocation();
        if(radiusFile.globalRulesList(world,"creatureSpawn", entityName, location)) {
            event.setCancelled(true);
        }

    }
}
