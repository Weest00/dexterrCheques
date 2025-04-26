package net.dexterr.cheques.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Vault {

    private static Economy econ = null;

    public static boolean setupEconomy(JavaPlugin plugin) {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Error trying to hook with vault. shuting down...");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().warning("Error trying to hook with economy plugin. shuting down...");
            return false;
        }

        econ = rsp.getProvider();
        plugin.getLogger().info("Hook with vault successfully completed.");
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
