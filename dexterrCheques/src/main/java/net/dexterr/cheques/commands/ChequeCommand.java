package net.dexterr.cheques.commands;

import net.dexterr.cheques.inventorys.ChequeInventory;
import net.dexterr.cheques.utils.Config;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChequeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Config.CONSOLE);
            return true;

        }

        Player player = (Player) sender;


        if (!player.hasPermission("dexterrcheques.use")) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
            player.sendMessage(Config.NO_PERMISSION);
            return true;
        }


        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
        ChequeInventory.openInventory(player);

        return true;
    }
}