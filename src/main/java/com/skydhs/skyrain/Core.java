package com.skydhs.skyrain;

import com.skydhs.skyrain.commands.RainCmd;
import com.skydhs.skyrain.integration.placeholderapi.PlaceholderIntegration;
import com.skydhs.skyrain.integration.vault.VaultIntegration;
import com.skydhs.skyrain.listener.InventoryClickListener;
import com.skydhs.skyrain.listener.WeatherListener;
import com.skydhs.skyrain.manager.RainManager;
import com.skydhs.skyrain.utils.FileUtils;
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
        new PlaceholderIntegration();
        new VaultIntegration();
        new RainManager(this);

        // -- Load the plugin commands and listeners -- \\
        sendMessage("Loading commands and listeners...");
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginCommand("paidrain").setExecutor(new RainCmd());

        sendMessage(ChatColor.YELLOW +  NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "has been enabled! Took " + getSpentTime(time) + "ms.");
        sendMessage("----------");
    }

    @Override
    public void onDisable() {
        sendMessage("----------");
        sendMessage(ChatColor.GRAY + "Disabling " + ChatColor.YELLOW +  NAME + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Version: " + ChatColor.YELLOW + VERSION + ChatColor.GRAY + "!");

        RainManager.getInstance().switchOff();

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