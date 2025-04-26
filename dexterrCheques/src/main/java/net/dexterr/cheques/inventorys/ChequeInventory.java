package net.dexterr.cheques.inventorys;

import net.dexterr.cheques.Main;
import net.dexterr.cheques.utils.Config;
import net.dexterr.cheques.utils.ItemBuilder;
import net.dexterr.cheques.utils.NumberUtils;
import net.dexterr.cheques.utils.Vault;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChequeInventory {


    public static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, Config.INVENTORY_TITLE);

        ItemStack headIcon = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("§a" + player.getName())
                .setLore("§7Your balance: §2$§f" + NumberUtils.formatDouble(Vault.getEconomy().getBalance(player)))
                .setSkullOwner(player).build();
        ItemStack chequeIcon = new ItemBuilder(Material.PAPER)
                .setName("§aCreate cheque!")
                .setLore("§7Store and transfer money easily.", "", "§7Default cheque: §2$§f" + NumberUtils.formatDouble(Config.DEFAULT_CHEQUE_PRICE), "", "§fRight-click ➞ §8Cheque with default value.", "§fLeft-click ➞ §8Choose a custom value.")
                .build();
        ItemStack closeIcon = new ItemBuilder(Material.GRAY_DYE)
                .setName("§aClose Inventory")
                .setLore("§7Click right here to close the inventory.")
                .hideAllFlags()
                .build();

        inventory.setItem(4, headIcon);
        inventory.setItem(22, chequeIcon);
        inventory.setItem(40, closeIcon);

        player.openInventory(inventory);


    }
}
