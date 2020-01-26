package com.skydhs.skyrain;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SkyRainLogger {
    private static final Logger LOGGER = Logger.getLogger(SkyRainLogger.class.getName().toUpperCase());

    /**
     * Log a message.
     *
     * @param level The message level.
     * @param message The message.
     */
    public static void log(Level level, String message) {
        SkyRainLogger.LOGGER.log(level, message);
    }

    /**
     * Log a message if the given condition
     * is true.
     *
     * @param condition Condition to verify.
     * @param level The message level.
     * @param message The message.
     */
    public static void logIf(Boolean condition, Level level, String message) {
        if (condition) {
            log(level, message);
        }
    }

    /**
     * Log a message.
     *
     * @param level The message level.
     * @param message The message.
     * @param thrown Throwable associated with
     *               log message.
     */
    public static void log(Level level, String message, Throwable thrown) {
        SkyRainLogger.LOGGER.log(level, message, thrown);
    }
}