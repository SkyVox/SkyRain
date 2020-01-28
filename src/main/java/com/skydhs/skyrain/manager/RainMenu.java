package com.skydhs.skyrain.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RainMenu {

    /**
     * Opens the rain inventory for
     * a specific player.
     *
     * @param player the inventory viewer.
     */
    public static void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "none?");
        player.openInventory(inventory);
    }
}