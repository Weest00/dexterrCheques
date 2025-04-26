package net.dexterr.cheques.utils;

import net.dexterr.cheques.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChequeUtils {

    private static final Set<UUID> chatLocked = new HashSet<>();
    private static final Material material = Material.PAPER;
    public static final NamespacedKey CHEQUE_VALUE_KEY = new NamespacedKey(Main.getInstance(), "cheque_value");
    public static final NamespacedKey CHEQUE_PLAYER_KEY = new NamespacedKey(Main.getInstance(), "cheque_player");


    public static void giveCheque(Player player, String playerName, double amount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.setDisplayName("§aCheque");
        meta.setLore(Arrays.asList("§7Store and transfer money easily.", "", "§7Made by: §f" + playerName, "§7Cheque value: §2$§f" + NumberUtils.formatDouble(amount), "","§fShift-click ➞ §8Use all stacked cheques at once.", "§fLeft-click ➞ §8Use only one cheque."));

        meta.getPersistentDataContainer().set(CHEQUE_VALUE_KEY, PersistentDataType.DOUBLE, amount);
        meta.getPersistentDataContainer().set(CHEQUE_PLAYER_KEY, PersistentDataType.STRING, playerName);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }


    public static boolean isChatLocked(UUID uuid) {
        return chatLocked.contains(uuid);
    }

    public static void lockChat(UUID uuid) {
        chatLocked.add(uuid);
    }

    public static void unlockChat(UUID uuid) {
        chatLocked.remove(uuid);
    }

}
