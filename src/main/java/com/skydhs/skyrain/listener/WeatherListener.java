package com.skydhs.skyrain.listener;

import com.skydhs.skyrain.WeatherType;
import com.skydhs.skyrain.manager.RainManager;
import com.skydhs.skyrain.utils.RainNMSUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WeatherListener implements Listener {
    private static final RainManager MANAGER = RainManager.getInstance();

//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
//    public void onWeatherChange(WeatherChangeEvent event) {
//        if (!MANAGER.isTaskRunning()) return;
//
//        final World world = event.getWorld();
//        if (world == null) return;
//
//        if (MANAGER.getCurrentTask().getWorld().getName().equalsIgnoreCase(world.getName())) {
//            event.setCancelled(true);
//        }
//    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        final World world = player.getWorld();

        if (!MANAGER.isTaskRunning()) return;
        WeatherType cachedWeather = MANAGER.getCachedWeather().remove(player.getUniqueId());

        if (cachedWeather == null && MANAGER.getCurrentTask().getWorld().getName().equalsIgnoreCase(world.getName())) {
            // This player has entered in a world that rain is currently enabled.
            RainNMSUtil.sendPacket(player, WeatherType.DOWNFALL);
            MANAGER.getCachedWeather().put(player.getUniqueId(), WeatherType.from(player.getPlayerWeather()));
        } else if (cachedWeather != null && !MANAGER.getCurrentTask().getWorld().getName().equalsIgnoreCase(world.getName())) {
            // Disable rain for this player.
            RainNMSUtil.sendPacket(player, cachedWeather);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!MANAGER.isTaskRunning()) return;
        WeatherType weather = MANAGER.getCachedWeather().remove(player.getUniqueId());

        if (weather != null) {
            RainNMSUtil.sendPacket(player, weather);
        }
    }
}