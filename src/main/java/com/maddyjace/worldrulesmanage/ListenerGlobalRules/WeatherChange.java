package com.maddyjace.worldrulesmanage.ListenerGlobalRules;

import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import com.maddyjace.worldrulesmanage.WorldRulesManage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {

    private final WorldFile worldFile = WorldFile.INSTANCE;

    private final WorldRulesManage plugin;
    public WeatherChange(WorldRulesManage plugin) {
        this.plugin = plugin;
    }

    // 控制下雨下雪
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        World world = event.getWorld();
        if(worldFile.globalRules(world.getName(),"fixedWeather")) {
            if (event.toWeatherState()) {
                event.setCancelled(true); // 保持晴天
                // 确保彻底恢复晴天
                Bukkit.getScheduler().runTask(plugin, () -> {
                    world.setStorm(false);
                    world.setThundering(false);
                    world.setWeatherDuration(0);
                });
            }
        }
    }

    // 控制雷暴雨
    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        World world = event.getWorld();
        if(worldFile.globalRules(world.getName(),"fixedWeather")) {
            if (event.toThunderState()) {
                // 有雷暴要开始，取消它
                event.setCancelled(true);
                // 确保彻底恢复晴天
                Bukkit.getScheduler().runTask(plugin, () -> {
                    world.setStorm(false);
                    world.setThundering(false);
                    world.setWeatherDuration(0);
                });
            }
        }
    }
}
