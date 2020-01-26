package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.task.DisableRainTask;
import org.bukkit.World;

public class RainManager {
    private static RainManager instance;

    private DisableRainTask currentTask = null;

    public RainManager() {
        RainManager.instance = this;
    }

    public Boolean startRain(World world) {
        if (isTaskRunning()) return false; // The world is raining already.

//        Player player = null;
//        player.setPlayerWeather(DOWNFALL.);

        world.setStorm(RainSettings.STORM);
        world.setThundering(RainSettings.THUNDERING);
        world.setThunderDuration(20 * RainSettings.DURATION);
        world.setWeatherDuration(20 * RainSettings.DURATION);

        // Rain has been enabled.
        return true;
    }

    public void disableRain(World world) {
        // TODO, disable rain in this world.
        currentTask = null;
    }

    public Boolean isTaskRunning() {
        return currentTask != null;
    }

    public static RainManager getInstance() {
        return instance;
    }
}