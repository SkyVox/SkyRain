package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

import static com.skydhs.skyrain.FileUtils.get;

public class RainMenu {
    public static final String INVENTORY_NAME = get().getString("Menu.info.title").getColored();
    public static final int INVENTORY_ROWS = (get().getInt("Menu.info.rows")*9);

    private static List<Integer> actionAbleButton = new ArrayList<>(12);

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
                    "%money_spent%",
                    "%is_raining%",
                    "%rain_remaining%",
            }, new String[] {
                    RainSettings.TOTAL_AMOUNT_SPENT.toString(),
                    RainManager.getInstance().isTaskRunning() ? RainSettings.BOOLEAN_ON : RainSettings.BOOLEAN_FALSE,
                    String.valueOf(RainManager.getInstance().getRemainderTime())
            });
            if (builder == null) continue;

            String action = get().getString("Menu.items." + str + ".action", "NONE").get().toUpperCase();
            String slot = get().getString("Menu.items." + str + ".slot").get();

            if (slot == null || slot.isEmpty()) {
                inventory.addItem(builder.build());
            } else {
                slot = slot.replaceAll(" ", "");

                if (slot.contains(",")) {
                    // This item will be placed in more than one slot.
                    for (String slots : slot.split(",")) {
                        int itemSlot = Integer.parseInt(slots);
                        inventory.setItem(itemSlot, builder.build());
                        addAction(itemSlot, action);
                    }
                } else {
                    // This item will be set in a single slot.
                    int itemSlot = Integer.parseInt(slot);
                    inventory.setItem(itemSlot, builder.build());
                    addAction(itemSlot, action);
                }
            }
        }

        player.openInventory(inventory);
    }

    private static void addAction(int slot, String action) {
        if (action.equals("START_RAIN") && !actionAbleButton.contains(slot)) {
            actionAbleButton.add(slot);
        }
    }

    public static List<Integer> getActionAbleButton() {
        return actionAbleButton;
    }
}