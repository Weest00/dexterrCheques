package net.dexterr.cheques.listeners;

import net.dexterr.cheques.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {


    @EventHandler
    private void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(Config.INVENTORY_TITLE)) return;
        event.setCancelled(true);
    }

}
