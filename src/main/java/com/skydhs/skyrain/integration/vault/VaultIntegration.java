package com.skydhs.skyrain.integration.vault;

import com.skydhs.skyrain.integration.Integration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultIntegration extends Integration {
    private static VaultIntegration instance;
    private Economy economy;

    public VaultIntegration() {
        super("Vault");

        VaultIntegration.instance = this;
    }

    @Override
    public Economy getMain() {
        if (!isEnabled()) return null;
        if (economy != null) return economy;

        RegisteredServiceProvider<Economy> response = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (response == null) return null;
        this.economy = response.getProvider();

        return economy;
    }

    public Boolean hasEnough(Player player, double value) {
        if (getMain() == null) return true;
        return getMain().getBalance(player) >= value;
    }

    public void takeValue(Player player, double value) {
        if (getMain() == null) return;
        getMain().withdrawPlayer(player, value);
    }

    public void addValue(Player player, double value) {
        if (getMain() == null) return;
        getMain().depositPlayer(player, value);
    }

    public static VaultIntegration getInstance() {
        return instance;
    }
}