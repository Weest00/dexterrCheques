package net.dexterr.cheques.listeners;

import net.dexterr.cheques.utils.ChequeUtils;
import net.dexterr.cheques.utils.Config;
import net.dexterr.cheques.utils.NumberUtils;
import net.dexterr.cheques.utils.Vault;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AsyncPlayerChatListener implements Listener {


    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = event.getPlayer().getUniqueId();
        String message = event.getMessage();

        if (!ChequeUtils.isChatLocked(playerUUID)) return;

        event.setCancelled(true);

        if (event.getMessage().equalsIgnoreCase("cancel")) {
            player.sendMessage(Config.CANCELED);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
            ChequeUtils.unlockChat(playerUUID);
            return;
        }


        if (!isDouble(message)) {
            player.sendMessage(Config.INVALID_NUMBER);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            return;
        }

        double amount = Double.parseDouble(message);

        if (amount <= 0 || message.equals("NaN")) {
            player.sendMessage(Config.INVALID_NUMBER);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            player.sendMessage(Config.INVENTORY_FULL);
            return;
        }

        if (Vault.getEconomy().getBalance(player) < amount){
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            player.sendMessage(Config.NO_MONEY.replace("<amount>", NumberUtils.formatDouble(amount)));
            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        player.sendMessage(Config.SUCCESS.replace("<amount>",NumberUtils.formatDouble(amount)));
        ChequeUtils.giveCheque(player, player.getName(), amount);
        ChequeUtils.unlockChat(playerUUID);
        Vault.getEconomy().withdrawPlayer(player,amount);

    }

    private boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
