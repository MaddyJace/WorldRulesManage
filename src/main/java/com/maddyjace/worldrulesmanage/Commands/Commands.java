package com.maddyjace.worldrulesmanage.Commands;

import com.maddyjace.worldrulesmanage.ConfigFile.MessageFile;
import com.maddyjace.worldrulesmanage.ConfigFile.WorldFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
public class Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            args = new String[] { "help" };
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "reload":
                WorldFile.INSTANCE.reload();
                MessageFile.INSTANCE.reload();
                // sender.sendMessage(ChatColor.DARK_GRAY + "[WorldManage]: " + ChatColor.GREEN + MessageFile.getMessage("Reload"));
                if(MessageFile.getMessage("PluginsName") != null && MessageFile.getMessage("Reload") != null) {
                    sender.sendMessage(MessageFile.setColors(MessageFile.getMessage("PluginsName")) +
                            MessageFile.setColors(MessageFile.getMessage("Reload")));
                }


                return true;
            default:
                // sender.sendMessage(ChatColor.DARK_GRAY + "[WorldManage]: " + ChatColor.DARK_RED + MessageFile.getMessage("CommandCorrectUsage"));
                if(MessageFile.getMessage("PluginsName") != null && MessageFile.getMessage("CommandCorrectUsage") != null) {
                    sender.sendMessage(MessageFile.setColors(MessageFile.getMessage("PluginsName")) +
                            MessageFile.setColors(MessageFile.getMessage("CommandCorrectUsage")));
                }
                return true;

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("worldmanage.admin")) {
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
