package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Get;
import com.maddyjace.worldrulesmanage.util.Ref;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.HashSet;
import java.util.Set;

/** 天气变化 事件监听器 */
public class WeatherChange implements Listener {

    private final Set<World> updatingWeather = new HashSet<>();

    @EventHandler
    public void event(WeatherChangeEvent e) {
        World world = e.getWorld(); // 世界对象
        if (world == null) return;
        if (updatingWeather.contains(world)) return;

        if (Ref.wdl.getGlobalData().containsKey(world.getName())) {
            int weatherChange = (int) Ref.wdl.getGlobalData().get(world.getName()).weatherChange;
            updatingWeather.add(world);
            try {
                switch (weatherChange) {
                    case 1: // 晴天
                        Bukkit.getScheduler().runTaskLater(Get.plugin(), () -> {
                            world.setStorm(false);
                            world.setThundering(false);
                        }, 5L);
                        break;
                    case 2: // 下雨/下雪
                        Bukkit.getScheduler().runTaskLater(Get.plugin(), () -> {
                            world.setStorm(true);
                            world.setThundering(false);
                        }, 5L);
                        break;
                    case 3: // 雷暴
                        Bukkit.getScheduler().runTaskLater(Get.plugin(), () -> {
                            world.setStorm(true);
                            world.setThundering(true);
                        }, 5L);
                        break;
                    default: return; // 自然天气
                }
                e.setCancelled(true);
            } finally {
                updatingWeather.remove(world);
            }
        }
    }

}
