package com.skydhs.skyrain.task;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class DisableRainTask extends BukkitRunnable {
    private final World world;
    private final long initialTime;

    public DisableRainTask(World world) {
        this.world = world;
        this.initialTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        // TODO.
        //  RainManager.getInstance().disableRain(world);
    }

    public World getWorld() {
        return world;
    }

    public long getInitialTime() {
        return initialTime;
    }
}