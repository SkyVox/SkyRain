package com.skydhs.skyrain.manager;

import com.skydhs.skyrain.FileUtils;

import java.math.BigDecimal;

public class RainSettings {

    /*
     * The world to rain.
     */
    public static final String WORLD = FileUtils.get().getString("Rain.world").get();

    /*
     * The rain time duration.
     */
    public static final int DURATION = FileUtils.get().getInt("Rain.duration");

    /*
     * Amount of money that this player should
     * pay to make the world rain.
     */
    public static final BigDecimal PAY_AMOUNT = new BigDecimal(FileUtils.get().getString("Rain.pay-amount").get());

    /*
     * Is thundering allowed during
     * the rain?
     */
    public static final Boolean THUNDERING = FileUtils.get().getBoolean("Rain.thundering");

    /*
     * Is storm allowed during
     * the rain?
     */
    public static final Boolean STORM = FileUtils.get().getBoolean("Rain.storm");

    /*
     * The amount total that the whole
     * server spent on rains.
     */
    public static BigDecimal TOTAL_AMOUNT_SPENT = new BigDecimal(FileUtils.get().getString(FileUtils.Files.CACHE, "total-money-spent").get());
}