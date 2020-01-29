package com.skydhs.skyrain.commands;

import com.skydhs.skyrain.FileUtils;
import com.skydhs.skyrain.manager.RainDenied;
import com.skydhs.skyrain.manager.RainManager;
import com.skydhs.skyrain.manager.RainMenu;
import com.skydhs.skyrain.manager.RainSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class RainCmd implements CommandExecutor {
    private static final List<String> HELP = new LinkedList<>();

    static {
        for (String str : FileUtils.get().getStringList("Messages.help").get()) {
            HELP.add(ChatColor.translateAlternateColorCodes('&', str));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length <= 0) {
            letItRain(player);
            return true;
        }

        String argument = args[0].toUpperCase();
        boolean executed = false;

        switch (argument) {
            case "HELP":
                executed = true;

                for (String str : HELP) {
                    sender.sendMessage(str);
                }

                break;
            case "MENU":
                executed = true;

                // Open the rain menu for @player.
                RainMenu.openMenu(player);
                break;
            case "STATS":
            case "STATUS":
                executed = true;

                for (String str : FileUtils.get().getStringList("Messages.status").getList(new String[] {
                        "%money-spent%",
                        "%is-raining%"
                }, new String[] {
                        RainSettings.TOTAL_AMOUNT_SPENT.toString(),
                        RainManager.getInstance().isTaskRunning() ? RainSettings.BOOLEAN_ON : RainSettings.BOOLEAN_FALSE
                })) {
                    sender.sendMessage(str);
                }

                break;
        }

        if (!executed) {
            Bukkit.dispatchCommand(sender, "paidrain help");
        }

        return true;
    }

    public static void letItRain(Player player) {
        RainDenied rain = RainManager.getInstance().letItRain(player);

        switch (rain) {
            case UNDEFINED: // Never Reachable..
                player.sendMessage(ChatColor.RED + "An error has occurred. Please contact the server administrator.");
                break;
            case SUCCESS:
                for (String str : FileUtils.get().getStringList("Messages.rain-enabled").getColored()) {
                    player.sendMessage(str);
                }
                break;
        }
    }
}