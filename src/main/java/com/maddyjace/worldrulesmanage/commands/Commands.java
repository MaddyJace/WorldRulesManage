package com.maddyjace.worldrulesmanage.commands;

import com.maddyjace.worldrulesmanage.WorldRulesManage;
import com.maddyjace.worldrulesmanage.util.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            args = new String[] { "help" };
        }

        String pluginName = Language.Get.translate(Language.getServerLanguage(), "pluginName");
        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "reload":
                WorldRulesManage.INSTANCE.onDisable();
                WorldRulesManage.INSTANCE.onEnable();
                sender.sendMessage(Language.Get.translate(Language.getServerLanguage(), "reload", pluginName));
                return true;
            default:
                sender.sendMessage(Language.Get.translate(Language.getServerLanguage(), "commandCorrectUsage", pluginName));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("worldrulesmanage.admin")) {
            return Collections.emptyList();
        }
        if (args.length == 1) {
            return Stream.of("help", "reload")
                    .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
