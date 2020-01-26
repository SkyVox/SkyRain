package com.skydhs.skyrain;

import com.skydhs.skyrain.integration.placeholderapi.PlaceholderIntegration;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {
    private static FileUtils instance;
    private Core core;

    private Files defaultFile = Files.CONFIG;

    private static final String CHARSET_NAME = "UTF-8";
    private static final String FILES_PATH = "files/%%file_name%%";

    /*
     * All loaded files.
     */
    private Map<Files, FileManager> files;

    public FileUtils(Core core) {
        FileUtils.instance = this;
        this.core = core;
        this.files = new HashMap<>(Files.values().length);

        for (Files files : Files.values()) {
            this.files.put(files, new FileManager(files));
        }
    }

    /**
     * Verify if contains
     * a specific path on
     * configuration file.
     *
     * @param path config path.
     * @return if path is valid.
     */
    public Boolean contains(String path) {
        return contains(defaultFile, path);
    }

    /**
     * Verify if contains
     * a specific path on
     * configuration file.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return if path is valid.
     */
    public Boolean contains(Files file, String path) {
        return getFile(file).get().contains(path);
    }

    /**
     * Get Boolean value.
     *
     * @param path config path.
     * @return config value.
     */
    public Boolean getBoolean(String path) {
        return getBoolean(defaultFile, path);
    }

    /**
     * Get Boolean value.
     *
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public Boolean getBoolean(String path, Boolean value) {
        return getBoolean(defaultFile, path, value);
    }

    /**
     * Get Boolean value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return config value.
     */
    public Boolean getBoolean(Files file, String path) {
        return StringUtils.equalsIgnoreCase(getFile(file).get().getString(path), "true");
    }

    /**
     * Get Boolean value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public Boolean getBoolean(Files file, String path, Boolean value) {
        return contains(file, path) ? getBoolean(file, path) : value;
    }

    /**
     * Get int value.
     *
     * @param path config path.
     * @return config value.
     */
    public int getInt(String path) {
        return getInt(defaultFile, path);
    }

    /**
     * Get int value.
     *
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public int getInt(String path, int value) {
        return getInt(defaultFile, path, value);
    }

    /**
     * Get int value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return config value.
     */
    public int getInt(Files file, String path) {
        return getFile(file).get().getInt(path);
    }

    /**
     * Get int value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public int getInt(Files file, String path, int value) {
        return contains(file, path) ? getInt(file, path) : value;
    }

    /**
     * Get long value.
     *
     * @param path config path.
     * @return config value.
     */
    public long getLong(String path) {
        return getLong(defaultFile, path);
    }

    /**
     * Get long value.
     *
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public long getLong(String path, long value) {
        return getLong(defaultFile, path, value);
    }

    /**
     * Get long value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return config value.
     */
    public long getLong(Files file, String path) {
        return getFile(file).get().getLong(path);
    }

    /**
     * Get long value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public long getLong(Files file, String path, long value) {
        return contains(file, path) ? getLong(file, path) : value;
    }

    /**
     * Get double value.
     *
     * @param path config path.
     * @return config value.
     */
    public double getDouble(String path) {
        return getDouble(defaultFile, path);
    }

    /**
     * Get double value.
     *
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public double getDouble(String path, double value) {
        return getDouble(defaultFile, path, value);
    }

    /**
     * Get double value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return config value.
     */
    public double getDouble(Files file, String path) {
        return getFile(file).get().getDouble(path);
    }

    /**
     * Get double value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public double getDouble(Files file, String path, double value) {
        return contains(file, path) ? getDouble(file, path) : value;
    }

    /**
     * Get float value.
     *
     * @param path config path.
     * @return config value.
     */
    public float getFloat(String path) {
        return getFloat(defaultFile, path);
    }

    /**
     * Get float value.
     *
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public float getFloat(String path, float value) {
        return getFloat(defaultFile, path, value);
    }

    /**
     * Get float value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return config value.
     */
    public float getFloat(Files file, String path) {
        return (float) getFile(file).get().getDouble(path);
    }

    /**
     * Get float value.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return config value.
     */
    public float getFloat(Files file, String path, float value) {
        return contains(file, path) ? getFloat(file, path) : value;
    }

    /**
     * Get a String from configuration
     * file.
     *
     * Using the {@link StringReplace},
     * You can replace the String
     * placeholders.
     *
     * @param path config path.
     * @return new object of {@link StringReplace}
     */
    public StringReplace getString(String path) {
        return getString(defaultFile, path);
    }

    /**
     * Get a String from configuration
     * file.
     *
     * Using the {@link StringReplace},
     * You can replace the String
     * placeholders.
     *
     * @param path config path.
     * @param value default value.
     * @return new object of {@link StringReplace}
     */
    public StringReplace getString(String path, String value) {
        return getString(defaultFile, path, value);
    }

    /**
     * Get a String from configuration
     * file.
     *
     * Using the {@link StringReplace},
     * You can replace the String
     * placeholders.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return new object of {@link StringReplace}
     */
    public StringReplace getString(Files file, String path) {
        return new StringReplace(getFile(file).get().getString(path));
    }

    /**
     * Get a String from configuration
     * file.
     *
     * Using the {@link StringReplace},
     * You can replace the String
     * placeholders.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return new object of {@link StringReplace}
     */
    public StringReplace getString(Files file, String path, String value) {
        return contains(file, path) ? getString(file, path) : new StringReplace(value);
    }

    /**
     * Get a List/Array from configuration
     * file.
     *
     * Using the {@link ListReplace},
     * You can replace the List/Array
     * placeholders.
     *
     * @param path config path.
     * @return new object of {@link ListReplace}
     */
    public ListReplace getStringList(String path) {
        return getStringList(defaultFile, path);
    }

    /**
     * Get a List/Array from configuration
     * file.
     *
     * Using the {@link ListReplace},
     * You can replace the List/Array
     * placeholders.
     *
     * @param path config path.
     * @param value default value.
     * @return new object of {@link ListReplace}
     */
    public ListReplace getStringList(String path, List<String> value) {
        return getStringList(defaultFile, path, value);
    }

    /**
     * Get a List/Array from configuration
     * file.
     *
     * Using the {@link ListReplace},
     * You can replace the List/Array
     * placeholders.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @return new object of {@link ListReplace}
     */
    public ListReplace getStringList(Files file, String path) {
        return new ListReplace(getFile(file).get().getStringList(path));
    }

    /**
     * Get a List/Array from configuration
     * file.
     *
     * Using the {@link ListReplace},
     * You can replace the List/Array
     * placeholders.
     *
     * @param file configuration file
     *             to get.
     * @param path config path.
     * @param value default value.
     * @return new object of {@link ListReplace}
     */
    public ListReplace getStringList(Files file, String path, List<String> value) {
        return contains(file, path) ? getStringList(file, path) : new ListReplace(value);
    }

    /**
     * Get file section.
     *
     * @param path
     * @return
     */
    public Set<String> getSection(String path) {
        return getSection(defaultFile, path);
    }

    /**
     * Get file section.
     *
     * @param path
     * @return
     */
    public Set<String> getSection(Files file, String path) {
        return getFile(file).get().getConfigurationSection(path).getKeys(false);
    }

    /**
     * Get a specific configuration
     * file.
     *
     * @param file configuration file
     *             to get.
     * @return configuration file.
     */
    public FileManager getFile(Files file) {
        return getFiles().get(file);
    }

    private Map<Files, FileManager> getFiles() {
        return files;
    }

    public static FileUtils get() {
        return instance;
    }

    private static void copy(InputStream is, File file, Logger logger) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len=is.read(buf)) > 0) {
                out.write(buf,0,len);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error has occurred while copying an configuration file.", ex);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "An error has occurred while closing OutputStream!", ex);
            }
        }
    }

    public enum Files {
        CONFIG("config", "yml"),
        CACHE("cache", "yml");

        public String name;
        public String extension;

        Files(String name, String extension) {
            this.name = name;
            this.extension = extension;
        }

        public String getName() {
            return name;
        }

        public String getExtension() {
            return extension;
        }
    }

    public class FileManager {
        private Files file;
        private File pdfile;
        private FileConfiguration language;

        public FileManager(Files file) {
            this.file = file;
            this.pdfile = new File(core.getDataFolder(), file.getName() + '.' + file.getExtension());

            if (!this.pdfile.exists()) {
                InputStream is = null;

                try {
                    pdfile.getParentFile().mkdirs();
                    pdfile.createNewFile();

                    is = core.getResource(file.getName() + '.' + file.getExtension());

                    if (is == null) {
                        copy(core.getResource(StringUtils.replace(FILES_PATH, "%%file_name%%", file.getName()) + '.' + file.getExtension()), pdfile, core.getLogger());
                    } else {
                        copy(is, pdfile, core.getLogger());
                    }
                } catch (IOException ex) {
                    core.getLogger().log(Level.SEVERE, "Could not create " + file.getName() + "!", ex);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException ex) {
                        core.getLogger().log(Level.SEVERE, "An error has occurred while closing InputStream!", ex);
                    }
                }
            }

            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(pdfile), CHARSET_NAME));
                language = YamlConfiguration.loadConfiguration(reader);
            } catch (UnsupportedEncodingException | FileNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (IOException ex) {
                    core.getLogger().log(Level.SEVERE, "An error has occurred while closing BufferedReader!", ex);
                }
            }
        }

        /**
         * Get fileConfiguration
         *
         * @return language.
         */
        public FileConfiguration get() {
            return language;
        }

        /**
         * Get file
         *
         * @return pdfile.
         */
        public File getFile() {
            return pdfile;
        }

        /**
         * Save configuration file
         *
         * @return if file has been saved.
         */
        public boolean save() {
            try {
                language.save(pdfile);
                return true;
            } catch (IOException ex) {
                core.getLogger().log(Level.SEVERE, "Could not save " + file.getName() + "!", ex);
            }

            return false;
        }

        /**
         * Reload configuration file
         *
         * @return if file has been reloaded.
         */
        public boolean reload() {
            try {
                language = YamlConfiguration.loadConfiguration(pdfile);
                return true;
            } catch (Exception ex) {
                core.getLogger().log(Level.SEVERE, "Could not reload " + file.getName() + "!", ex);
            }

            return false;
        }
    }

    public static class StringReplace {
        private String str;

        public StringReplace(String str) {
            this.str = str;
        }

        /**
         * Get raw value.
         * This is the non edited/changed
         * String.
         *
         * @return the original String.
         */
        public String get() {
            return str;
        }

        public String getColored() {
            return ChatColor.translateAlternateColorCodes('&', str);
        }

        public String getString(String[] placeholders, String[] replaces) {
            return getString(placeholders, replaces, true);
        }

        public String getString(String[] placeholders, String[] replaces, Boolean useColor) {
            return setPlaceholder(placeholders, replaces, useColor);
        }

        public String setPlaceholder(Player player) {
            return PlaceholderIntegration.getInstance().setPlaceholder(player, str);
        }

        public String setPlaceholder(Player player, String[] placeholders, String[] replaces) {
            this.str = PlaceholderIntegration.getInstance().setPlaceholder(player, str);
            return setPlaceholder(placeholders, replaces, false);
        }

        private String setPlaceholder(String[] placeholders, String[] replaces, Boolean useColor) {
            if (str == null || str.isEmpty()) return str;

            if (placeholders != null && replaces != null && placeholders.length == replaces.length) {
                str = StringUtils.replaceEach(str, placeholders, replaces);
            }

            if (useColor) return ChatColor.translateAlternateColorCodes('&', str);
            return str;
        }
    }

    public static class ListReplace {
        private String[] str;

        public ListReplace(String[] str) {
            this.str = str;
        }

        public ListReplace(List<String> list) {
            if (list != null) {
                this.str = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    this.str[i] = list.get(i);
                }
            } else {
                this.str = null;
            }
        }

        /**
         * Get raw Array.
         * This is the non edited/changed
         * array.
         *
         * @return the original array.
         */
        public String[] get() {
            return str;
        }

        public List<String> asList() {
            if (str == null || str.length <= 0) return null;

            List<String> ret = new ArrayList<>(str.length);

            for (String str : this.str) {
                ret.add(str);
            }

            return ret;
        }

        public Set<String> asSet() {
            if (str == null || str.length <= 0) return null;

            Set<String> ret = new HashSet<>(str.length);

            for (String str : this.str) {
                ret.add(str);
            }

            return ret;
        }

        public String[] getColored() {
            if (str == null || str.length <= 0) return null;

            String[] ret = new String[str.length];

            for (int i = 0; i < str.length; i++) {
                ret[i] = ChatColor.translateAlternateColorCodes('&', str[i]);
            }

            return ret;
        }

        public String[] getList(String[] placeholders, String[] replaces) {
            return getList(placeholders, replaces, true);
        }

        public String[] getList(String[] placeholders, String[] replaces, Boolean useColor) {
            return setPlaceholder(placeholders, replaces, useColor);
        }

        public String[] setPlaceholder(Player player) {
            return PlaceholderIntegration.getInstance().setPlaceholder(player, this.str);
        }

        public String[] setPlaceholder(Player player, String[] placeholders, String[] replaces) {
            this.str = PlaceholderIntegration.getInstance().setPlaceholder(player, str);
            return setPlaceholder(placeholders, replaces, false);
        }

        private String[] setPlaceholder(String[] placeholders, String[] replaces, Boolean useColor) {
            if (str == null || str.length <= 0) return str;

            // Create new instance.
            String[] ret = new String[str.length];

            if (placeholders != null && replaces != null && placeholders.length == replaces.length) {
                for (int i = 0; i < str.length; i++) {
                    ret[i] = useColor ? ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(str[i], placeholders, replaces)) : StringUtils.replaceEach(str[i], placeholders, replaces);
                }
            } else if (useColor) {
                for (int i = 0; i < str.length; i++) {
                    ret[i] = ChatColor.translateAlternateColorCodes('&', str[i]);
                }
            }

            return ret;
        }
    }
}