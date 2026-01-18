package com.maddyjace.worldrulesmanage;

import com.maddyjace.worldrulesmanage.commands.Commands;
import com.maddyjace.worldrulesmanage.util.AutoReload;
import com.maddyjace.worldrulesmanage.util.Config;
import com.maddyjace.worldrulesmanage.util.Get;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

    private static AutoReload autoReload;

    @Override
    public void onEnable() {
        Get.initialize(this);

        // 注册 命令 和 Tab键 监听器
        Commands commandHandler = new Commands();
        this.getCommand("worldrulesmanage").setExecutor(commandHandler);     // 命令
        this.getCommand("worldrulesmanage").setTabCompleter(commandHandler); // Tab
        WorldRulesManage.INSTANCE.onEnable();

        Config c = Config.INSTANCE;
        if (c.autoReloadEnable) {
            long second = c.autoReloadPeriod * 1000L;
            autoReload = new AutoReload(getDataFolder().getPath(), second, true,".yml", ".yaml");
            autoReload.start();
        }

        // 颜色
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String YELLOW = "\u001B[33m";
        final String GREEN = "\u001B[32m";
        Get.plugin().getLogger().info("\n" + "\n" +
                BLUE + " _       __           __    ____  ___                                  \n" +
                BLUE + "| |     / /___  _____/ /___/ /  |/  /___ _____  ____ _____ ____        \n" +
                BLUE + "| | /| / / __ \\/ ___/ / __  / /|_/ / __ `/ __ \\/ __ `/ __ `/ _ \\    \n" +
                BLUE + "| |/ |/ / /_/ / /  / / /_/ / /  / / /_/ / / / / /_/ / /_/ /  __/       \n" +
                BLUE + "|__/|__/\\____/_/  /_/\\__,_/_/  /_/\\__,_/_/ /_/\\__,_/\\__, /\\___/  \n" +
                BLUE + "                                                   /____/              \n\n" +
                YELLOW + "Our community address\n" +
                RESET + "QQ: https://discord.gg/xCKeUReG\n" +
                GREEN + "Author: MaddyJace  FeedbackEmail: dixiaomai@qq.com  Version: 1.0\n" +
                RESET + "-------------------------------------------------------------------------------------"
        );

    }

    @Override
    public void onDisable() {
        WorldRulesManage.INSTANCE.onDisable();

        if (autoReload != null) {
            autoReload.stop();
            autoReload = null;
        }

    }

}
