package com.skydhs.skyrain.integration.placeholderapi;

import com.skydhs.skyrain.integration.Integration;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderIntegration extends Integration {
    private static PlaceholderIntegration instance;

    public PlaceholderIntegration() {
        super("PlaceholderAPI");

        PlaceholderIntegration.instance = this;
    }

    @Override
    public Object getMain() {
        return me.clip.placeholderapi.PlaceholderAPIPlugin.getInstance();
    }

    public String setPlaceholder(final Player player, final String text) {
        if (text == null || text.isEmpty()) return text;
        if (!isEnabled()) return text;
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
    }

    public String[] setPlaceholder(final Player player, final String[] text) {
        if (text == null || text.length <= 0) return text;
        if (!isEnabled()) return text;

        String[] ret = new String[text.length];

        for (int i = 0; i < text.length; i++) {
            ret[i] = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text[i]);
        }

        return ret;
    }

    public List<String> setPlaceholder(final Player player, final List<String> text) {
        if (text == null || text.isEmpty()) return text;
        if (!isEnabled()) return text;
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
    }

    public static PlaceholderIntegration getInstance() {
        return instance;
    }
}