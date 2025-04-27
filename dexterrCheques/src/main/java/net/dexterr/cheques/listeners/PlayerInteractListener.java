package net.dexterr.cheques.listeners;

import net.dexterr.cheques.utils.ChequeUtils;
import net.dexterr.cheques.utils.Config;
import net.dexterr.cheques.utils.NumberUtils;
import net.dexterr.cheques.utils.Vault;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractListener implements Listener {

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        if (meta.getPersistentDataContainer().has(ChequeUtils.CHEQUE_PLAYER_KEY, PersistentDataType.STRING)) {
            Double amount = meta.getPersistentDataContainer().get(ChequeUtils.CHEQUE_VALUE_KEY, PersistentDataType.DOUBLE);
            String creator = meta.getPersistentDataContainer().get(ChequeUtils.CHEQUE_PLAYER_KEY, PersistentDataType.STRING);

            if (amount == null || creator == null)
                return;

            if (player.isSneaking()) {
                int quantity = item.getAmount();
                double totalAmount = amount * quantity;

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                player.sendMessage(Config.USE_CHEQUE.replace("<amount>", NumberUtils.formatDouble(totalAmount)));
                item.setAmount(0);
                Vault.getEconomy().depositPlayer(player, totalAmount);
            } else {

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
                player.sendMessage(Config.USE_CHEQUE.replace("<amount>", NumberUtils.formatDouble(amount)));
                item.setAmount(item.getAmount() - 1);
                Vault.getEconomy().depositPlayer(player, amount);
            }

        }
    }
}
