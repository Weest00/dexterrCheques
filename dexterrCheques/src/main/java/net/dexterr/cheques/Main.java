package net.dexterr.cheques;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import net.dexterr.cheques.utils.Config;
import net.dexterr.cheques.utils.Vault;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private static Main instance;


    @Override
    public void onEnable() {
        instance = this;

        if (!Vault.setupEconomy(this)) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        Config.load(this);
        setupCommands();
        setupListeners();

        getLogger().info("Contact: https://www.youtube.com/@dexterrplugins");

    }

    public void setupListeners() {
        try {
            ImmutableSet<ClassPath.ClassInfo> infoList = ClassPath.from(getClassLoader())
                    .getTopLevelClassesRecursive("net.dexterr.cheques.listeners");
            for (ClassPath.ClassInfo info : infoList) {
                Class<?> cls = Class.forName(info.getName());
                if (Listener.class.isAssignableFrom(cls)) {
                    Listener listener = (Listener) cls.getDeclaredConstructor().newInstance();
                    Bukkit.getPluginManager().registerEvents(listener, this);
                    Bukkit.getLogger().info("Loaded listener: " + cls.getSimpleName());
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error while trying to load the listeners!");
        }
    }

    public void setupCommands() {
        try {
            ImmutableSet<ClassPath.ClassInfo> infoList = ClassPath.from(getClassLoader())
                    .getTopLevelClassesRecursive("net.dexterr.cheques.commands");
            for (ClassPath.ClassInfo info : infoList) {
                Class<?> cls = Class.forName(info.getName());
                if (CommandExecutor.class.isAssignableFrom(cls)) {
                    String commandName = info.getSimpleName().replace("Command", "").toLowerCase();
                    PluginCommand command = getCommand(commandName);
                    if (command != null) {
                        CommandExecutor executor = (CommandExecutor) cls.getDeclaredConstructor().newInstance();
                        command.setExecutor(executor);
                        Bukkit.getLogger().info("Loaded command: /" + commandName);
                    } else {
                        Bukkit.getLogger().warning("Command not found in plugin.yml: " + commandName);
                    }
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error while trying to load the commands!");
        }
    }


    public static Main getInstance() {
        return instance;
    }
}
