package com.maddyjace.worldrulesmanage.globalrules;

import com.maddyjace.worldrulesmanage.util.Get;
import com.maddyjace.worldrulesmanage.util.Ref;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

/** 时间变化 */
public class TimeChange {

    private static BukkitTask timeTask;

    public static void onEnable() {
        timeTask = Bukkit.getScheduler().runTaskTimer(Get.plugin(), () -> {
            for (String str : Ref.wdl.getGlobalData().keySet()) {
                World world = Bukkit.getWorld(str);
                if (world == null) return;
                long time = Ref.wdl.getGlobalData().get(str).timeChange;
                if (time != -1) {
                    world.setTime(time);
                }
            }
        }, 20L, 20L);
    }

    public static void onDisable() {
        if (timeTask != null) {
            timeTask.cancel();
            timeTask = null;
        }
    }

}
