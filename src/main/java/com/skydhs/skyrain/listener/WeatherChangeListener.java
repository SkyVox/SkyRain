package com.skydhs.skyrain.listener;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onWeatherChange(WeatherChangeEvent event) {
        final World world = event.getWorld();
        if (world == null) return;
    }
}