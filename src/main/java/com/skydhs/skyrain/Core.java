package com.skydhs.skyrain;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    private final String NAME = getDescription().getName();
    private final String VERSION = getDescription().getVersion();

    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        sendMessage("----------");
        sendMessage(ChatColor.GRAY + "Enabling " + ChatColor.YELLOW + NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Version: " + ChatColor.YELLOW + VERSION + ChatColor.GRAY + "!");

        // -- Generate and setup the configuration files -- \\
        sendMessage("Loading the configuration files...");
        new FileUtils(this);

        // -- Load all classes instances and the plugin dependencies -- \\
        sendMessage("Loading dependencies and instances...");

        // -- Load the plugin commands and listeners -- \\
        sendMessage("Loading commands and listeners...");

        sendMessage(ChatColor.YELLOW +  NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "has been enabled! Took " + getSpentTime(time) + "ms.");
        sendMessage("----------");
    }

    @Override
    public void onDisable() {
        sendMessage("----------");
        sendMessage(ChatColor.GRAY + "Disabling " + ChatColor.YELLOW +  NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Version: " + ChatColor.YELLOW + VERSION + ChatColor.GRAY + "!");
        sendMessage(ChatColor.YELLOW +  NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "has been disabled!");
        sendMessage("----------");
    }

    private void sendMessage(String text) {
        console.sendMessage(text.replace('&', '§'));
    }

    private long getSpentTime(long time) {
        return (System.currentTimeMillis() - time);
    }
}