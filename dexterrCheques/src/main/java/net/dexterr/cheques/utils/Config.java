package net.dexterr.cheques.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    public static String CONSOLE;
    public static String INVENTORY_FULL;
    public static String CANCELED;
    public static String INVALID_NUMBER;
    public static String NO_MONEY;
    public static String SUCCESS;
    public static String RELEASE_SHIFT;
    public static String TYPE;
    public static String INVENTORY_TITLE;
    public static String USE_CHEQUE;
    public static String NO_PERMISSION;
    public static double DEFAULT_CHEQUE_PRICE;

    public static void load(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        CONSOLE = color(config.getString("messages.console"));
        INVENTORY_FULL = color(config.getString("messages.inventory-full"));
        CANCELED = color(config.getString("messages.canceled"));
        INVALID_NUMBER = color(config.getString("messages.invalid-number"));
        NO_MONEY = color(config.getString("messages.no-money"));
        SUCCESS = color(config.getString("messages.success"));
        RELEASE_SHIFT = color(config.getString("messages.release-shift"));
        TYPE = color(config.getString("messages.type"));
        INVENTORY_TITLE = color(config.getString("settings.inventory-title"));
        USE_CHEQUE = color(config.getString("messages.use-cheque"));
        NO_PERMISSION = color(config.getString("messages.no-permission"));
        DEFAULT_CHEQUE_PRICE = config.getDouble("settings.default-cheque-price");
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
