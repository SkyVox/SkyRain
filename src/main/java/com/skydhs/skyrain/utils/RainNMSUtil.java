package com.skydhs.skyrain.utils;

import com.skydhs.skyrain.WeatherType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class RainNMSUtil {
    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static final boolean SIXTEEN = Integer.parseInt(StringUtils.split(VERSION, "_")[1]) >= 16;

    /**
     * Empty constructor.
     */
    public RainNMSUtil() {
    }

    public static void sendPacket(Player player, WeatherType type) {
        Object packet = null;

        try {
            Constructor<?> gameStateChangeConstructor = null;

            if (SIXTEEN) {
                gameStateChangeConstructor = ClassWrapper.GAME_STATE_CHANGE.getClazz().getConstructor(ClassWrapper.GAME_STATE_CHANGE.getClazz().getField(type.getField()).get((Object) null).getClass(), Float.TYPE);
                packet = gameStateChangeConstructor.newInstance(ClassWrapper.GAME_STATE_CHANGE.getClazz().getField(type.getField()).get((Object) null), type.getState());
            } else {
                gameStateChangeConstructor = ClassWrapper.GAME_STATE_CHANGE.getClazz().getConstructor(Integer.TYPE, Float.TYPE);
                packet = gameStateChangeConstructor.newInstance(type.getType(), type.getState());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (packet != null) {
            try {
                Object handle = Objects.requireNonNull(ClassWrapper.CRAFT_PLAYER.getClazz()).getMethod("getHandle").invoke(player);
                Object connection = handle.getClass().getField("playerConnection").get(handle);
                connection.getClass().getMethod("sendPacket", ClassWrapper.PACKET.getClazz()).invoke(connection, packet);
            } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    private enum ClassWrapper {
        PACKET(PackageWrapper.NMS, "Packet"),
        CRAFT_PLAYER(PackageWrapper.CRAFT_BUKKIT, "entity.CraftPlayer"),
        GAME_STATE_CHANGE(PackageWrapper.NMS, "PacketPlayOutGameStateChange");

        // Part of Custom Rain.
//        SPAWN_ENTITY_WEATHER(PackageWrapper.NMS, "PacketPlayOutSpawnEntityWeather"),
//        ENTITY_LIGHTNING(PackageWrapper.NMS, "EntityLightning");

        private Class<?> clazz;
        private PackageWrapper packageWrapper;
        private String className;

        ClassWrapper(PackageWrapper packageWrapper, String className) {
            this.clazz = ClassWrapper.getClazz(packageWrapper.getUri() + '.' + VERSION + '.' + className);
            this.packageWrapper = packageWrapper;
            this.className = className;
        }

        public PackageWrapper getPackageWrapper() {
            return packageWrapper;
        }

        public String getClassName() {
            return className;
        }

        public final Class<?> getClazz() {
            return clazz;
        }

        public Object getNewInstance() {
            try {
                return getClazz().newInstance();
            } catch (IllegalAccessException | InstantiationException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        private static Class<?> getClazz(final String name) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    private enum PackageWrapper {
        CRAFT_BUKKIT("org.bukkit.craftbukkit"),
        NMS("net.minecraft.server");

        private String uri;

        PackageWrapper(String uri) {
            this.uri = uri;
        }

        public String getUri() {
            return uri;
        }
    }
}