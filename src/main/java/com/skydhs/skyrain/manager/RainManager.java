package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.Core;
import com.skydhs.skyrain.integration.vault.VaultIntegration;
import com.skydhs.skyrain.task.DisableRainTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.MathContext;

import static com.skydhs.skyrain.FileUtils.get;

public class RainManager {
    private static RainManager instance;

    private Core core;
    private DisableRainTask currentTask = null;

    public RainManager(Core core) {
        RainManager.instance = this;

        this.core = core;
    }

    public void switchOff() {
        if (isTaskRunning()) {
            disableRain(currentTask.getWorld());
        }
    }

    public void letItRain(Player player) {
        if (isTaskRunning()) {
            player.sendMessage(get().getString("Messages.already-raining").getString(new String[] {
                    "%rain_remaining%"
            }, new String[] {
                    String.valueOf(getRemainderTime())
            }));
        }

        if (!VaultIntegration.getInstance().hasEnough(player, RainSettings.PAY_AMOUNT.doubleValue())) {
            player.sendMessage(get().getString("Messages.no-enough-money").getColored());
            return;
        }

        VaultIntegration.getInstance().takeValue(player, RainSettings.PAY_AMOUNT.doubleValue());
        RainSettings.TOTAL_AMOUNT_SPENT = RainSettings.TOTAL_AMOUNT_SPENT.add(RainSettings.PAY_AMOUNT, MathContext.DECIMAL128);

        if (!startRain(getWorld(player))) {
            player.sendMessage(get().getString("Messages.already-raining").getString(new String[] {
                    "%rain_remaining%"
            }, new String[] {
                    String.valueOf(getRemainderTime())
            }));
        }
    }

    public World getWorld(Player player) {
        final String world = RainSettings.WORLD;
        if (world.equalsIgnoreCase("PLAYER")) return player.getWorld();

        for (World worlds : Bukkit.getWorlds()) {
            if (worlds.getName().equalsIgnoreCase(world)) return worlds;
        }

        return null;
    }

    public Boolean startRain(World world) {
        if (isTaskRunning()) return false; // The world is raining already.

//        Player player = null;
//        player.setPlayerWeather(DOWNFALL.);

        world.setStorm(RainSettings.STORM);
        world.setThundering(RainSettings.THUNDERING);
        world.setThunderDuration(20 * RainSettings.DURATION);
        world.setWeatherDuration(20 * RainSettings.DURATION);

        this.currentTask = new DisableRainTask(world);
        this.currentTask.runTaskLaterAsynchronously(core, 20 * RainSettings.DURATION);

        // Rain has been enabled.
        return true;
    }

    public void disableRain(final World world) {
        if (!Bukkit.isPrimaryThread()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    disableRain(world);
                }
            }.runTask(core);

            return;
        }

        world.setStorm(false);
        world.setThundering(false);
        world.setThunderDuration(0);
        world.setWeatherDuration(0);

        currentTask.cancel();
        currentTask = null;
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

    public static RainManager getInstance() {
        return instance;
    }
}