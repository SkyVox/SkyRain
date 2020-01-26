package com.skydhs.skyrain.task;

import com.skydhs.skyrain.manager.RainManager;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class DisableRainTask extends BukkitRunnable {
    private final World world;

    public DisableRainTask(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        RainManager.getInstance().disableRain(world);
    }
}