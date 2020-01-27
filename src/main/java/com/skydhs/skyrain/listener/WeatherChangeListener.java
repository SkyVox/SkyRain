package com.skydhs.skyrain.listener;

import com.skydhs.skyrain.manager.RainManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    private static final RainManager MANAGER = RainManager.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (!MANAGER.isTaskRunning()) return;

        final World world = event.getWorld();
        if (world == null) return;

        if (MANAGER.getCurrentTask().getWorld().getName().equalsIgnoreCase(world.getName())) {
            event.setCancelled(true);
        }
    }
}