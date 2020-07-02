package com.skydhs.skyrain;

public enum WeatherType {
    DOWNFALL(2, 0.0F, "c" /* Represents the DownFall value. */),
    THUNDERING(2, 0.0F, "c"), // TODO, Custom Rain.
    CLEAR(1, 0.0F, "b" /* Represents the Clear value. */);

    private int type;
    private float state;

    // Fields are only used in 1.16 or higher.
    private String field;

    WeatherType(int type, float state, String field) {
        this.type = type;
        this.state = state;
        this.field = field;
    }

    public int getType() {
        return type;
    }

    public float getState() {
        return state;
    }

    public String getField() {
        return field;
    }

    public static WeatherType from(org.bukkit.WeatherType type) {
        if (type == null) return WeatherType.CLEAR;
        return type.equals(org.bukkit.WeatherType.DOWNFALL) ? WeatherType.DOWNFALL : WeatherType.CLEAR;
    }
}