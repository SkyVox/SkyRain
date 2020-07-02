package com.skydhs.skyrain.listener;

import com.skydhs.skyrain.RainMenu;
import com.skydhs.skyrain.commands.RainCmd;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();

        /* Player clicked outside the inventory. */
        if (slot == -999) return;
        if (item == null || item.getType().equals(Material.AIR)) return;
        if (!title.equalsIgnoreCase(RainMenu.INVENTORY_NAME)) return;

        event.setCancelled(true);
        if ((event.getClick() == ClickType.SHIFT_LEFT) || (event.getClick() == ClickType.SHIFT_RIGHT)) {
            event.setResult(InventoryClickEvent.Result.DENY);
            return;
        }

        if (event.getClickedInventory() == player.getInventory()) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        if (!RainMenu.getActionAbleButton().contains(slot)) return;

        // Try to start rain.
        RainCmd.letItRain(player);

        // Finally close the player inventory.
        player.closeInventory();
    }
}