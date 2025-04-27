package net.dexterr.cheques.listeners;

import net.dexterr.cheques.utils.ChequeUtils;
import net.dexterr.cheques.utils.Config;
import net.dexterr.cheques.utils.NumberUtils;
import net.dexterr.cheques.utils.Vault;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {


    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(Config.INVENTORY_TITLE)) return;

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.isShiftClick()) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            player.sendMessage(Config.RELEASE_SHIFT);
            return;
        }

        Inventory clicked = event.getClickedInventory();
        Inventory top = event.getView().getTopInventory();

        if (clicked == null || !clicked.equals(top)) return;

        ClickType clickType = event.getClick();
        int slot = event.getRawSlot();

        switch (slot) {
            case 22:
                if (clickType == ClickType.LEFT) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                    player.closeInventory();
                    player.sendMessage(Config.TYPE);
                    ChequeUtils.lockChat(player.getUniqueId());
                    return;
                }

                if (player.getInventory().firstEmpty() == -1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
                    player.sendMessage(Config.INVENTORY_FULL);
                    player.closeInventory();
                    return;
                }


                if (Vault.getEconomy().getBalance(player) < Config.DEFAULT_CHEQUE_PRICE){
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
                    player.closeInventory();
                    player.sendMessage(Config.NO_MONEY.replace("<amount>", NumberUtils.formatDouble(Config.DEFAULT_CHEQUE_PRICE)));
                    return;
                }

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                player.closeInventory();
                player.sendMessage(Config.SUCCESS.replace("<amount>", NumberUtils.formatDouble(Config.DEFAULT_CHEQUE_PRICE)));
                ChequeUtils.giveCheque(player, player.getName(),Config.DEFAULT_CHEQUE_PRICE);
                Vault.getEconomy().withdrawPlayer(player,Config.DEFAULT_CHEQUE_PRICE);
                break;

            case 40:
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1F, 1F);
                player.closeInventory();
                break;
        }
    }
}
