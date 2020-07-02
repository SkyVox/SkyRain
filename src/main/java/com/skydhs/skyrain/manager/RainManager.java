package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.Core;
import com.skydhs.skyrain.RainSettings;
import com.skydhs.skyrain.WeatherType;
import com.skydhs.skyrain.integration.vault.VaultIntegration;
import com.skydhs.skyrain.task.DisableRainTask;
import com.skydhs.skyrain.utils.FileUtils;
import com.skydhs.skyrain.utils.RainNMSUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.math.MathContext;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.skydhs.skyrain.utils.FileUtils.get;

public class RainManager {
    private static RainManager instance;

    private Core core;
    private DisableRainTask currentTask = null;

    private Map<UUID, WeatherType> cachedWeather = new ConcurrentHashMap<>(128);

    public RainManager(Core core) {
        RainManager.instance = this;

        this.core = core;
    }

    public void switchOff() {
        if (isTaskRunning()) {
            disableRain(currentTask.getWorld());
        }
    }

    public RainResponse letItRain(Player player) {
        if (isTaskRunning()) {
            player.sendMessage(get().getString("Messages.already-raining").getString(new String[] {
                    "%rain_remaining%"
            }, new String[] {
                    String.valueOf(getRemainderTime())
            }));
            return RainResponse.ALREADY_RAINING;
        }

        if (!VaultIntegration.getInstance().hasEnough(player, RainSettings.PAY_AMOUNT.doubleValue())) {
            player.sendMessage(get().getString("Messages.no-enough-money").getColored());
            return RainResponse.NO_MONEY;
        }

        VaultIntegration.getInstance().takeValue(player, RainSettings.PAY_AMOUNT.doubleValue());
        RainSettings.TOTAL_AMOUNT_SPENT = RainSettings.TOTAL_AMOUNT_SPENT.add(RainSettings.PAY_AMOUNT, MathContext.DECIMAL128);

        if (!startRain(getWorld(player))) {
            player.sendMessage(get().getString("Messages.already-raining").getString(new String[] {
                    "%rain_remaining%"
            }, new String[] {
                    String.valueOf(getRemainderTime())
            }));
            return RainResponse.ALREADY_RAINING;
        }

        return RainResponse.SUCCESS;
    }

    public Boolean startRain(World world) {
        if (isTaskRunning()) return false; // The world is raining already.

        world.getPlayers().forEach(player -> {
            // Cache the old weather type from this player.
            WeatherType old = WeatherType.from(player.getPlayerWeather());
            cachedWeather.put(player.getUniqueId(), old);

            RainNMSUtil.sendPacket(player, WeatherType.DOWNFALL);
        });

        world.setStorm(RainSettings.STORM);
        world.setThundering(RainSettings.THUNDERING);
        world.setWeatherDuration(20 * RainSettings.DURATION);

        this.currentTask = new DisableRainTask(world);
        this.currentTask.runTaskLaterAsynchronously(core, 20 * RainSettings.DURATION);

        // Rain has been enabled.
        return true;
    }

    public void disableRain(final World world) {
        world.getPlayers().forEach(player -> {
            WeatherType weather = cachedWeather.get(player.getUniqueId());
            if (weather != null) {
                RainNMSUtil.sendPacket(player, weather);
            }
        });

        world.setStorm(false);
        world.setThundering(false);

        currentTask.cancel();
        currentTask = null;
        this.cachedWeather.clear();

        // Save the money spent.
        get().getFile(FileUtils.Files.CACHE).get().set("total-money-spent", RainSettings.TOTAL_AMOUNT_SPENT.toString());
        get().getFile(FileUtils.Files.CACHE).save();
    }

    private World getWorld(Player player) {
        final String world = RainSettings.WORLD;
        if (world.equalsIgnoreCase("PLAYER")) return player.getWorld();

        for (World worlds : Bukkit.getWorlds()) {
            if (worlds.getName().equalsIgnoreCase(world)) return worlds;
        }

        return Bukkit.getWorlds().get(0); // Get the main world.
    }

    public int getRemainderTime() {
        if (!isTaskRunning()) return 0;
        long value = System.currentTimeMillis() - currentTask.getInitialTime();
        return (int) (RainSettings.DURATION - value);
    }

    public DisableRainTask getCurrentTask() {
        return currentTask;
    }

    public Boolean isTaskRunning() {
        return currentTask != null;
    }

    public Map<UUID, WeatherType> getCachedWeather() {
        return cachedWeather;
    }

    public static RainManager getInstance() {
        return instance;
    }
}