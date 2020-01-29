package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static com.skydhs.skyrain.FileUtils.get;

public class RainMenu {
    public static final String INVENTORY_NAME = get().getString("Menu.info.title").getColored();
    public static final int INVENTORY_ROWS = (get().getInt("Menu.info.rows")*9);

    /**
     * Opens the rain inventory for
     * a specific player.
     *
     * @param player the inventory viewer.
     */
    public static void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_ROWS, INVENTORY_NAME);

        for (String str : get().getSection("Menu.items")) {
            ItemBuilder builder = ItemBuilder.get(get().getFile(FileUtils.Files.CONFIG).get(), "Menu.items." + str, new String[] {
                    "%money-spent%",
                    "%is-raining%",
                    "%rain_remaining%",
            }, new String[] {
                    RainSettings.TOTAL_AMOUNT_SPENT.toString(),
                    RainManager.getInstance().isTaskRunning() ? RainSettings.BOOLEAN_ON : RainSettings.BOOLEAN_FALSE,
                    String.valueOf(RainManager.getInstance().getRemainderTime())
            });
            if (builder == null) continue;

            String slot = get().getString("Menu.items." + str + ".slot").get();

            if (slot == null || slot.isEmpty()) {
                inventory.addItem(builder.build());
            } else {
                slot = slot.replaceAll(" ", "");

                if (slot.contains(",")) {
                    // This item will be placed in more than one slot.
                    for (String slots : slot.split(",")) {
                        inventory.setItem(Integer.parseInt(slots), builder.build());
                    }
                } else {
                    // This item will be set in a single slot.
                    inventory.setItem(Integer.parseInt(slot), builder.build());
                }
            }
        }

        player.openInventory(inventory);
    }
}