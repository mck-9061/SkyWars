package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.KitGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitGuiOpenListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!Main.pregame.contains(event.getPlayer())) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.CHEST)) return;

        Game game = null;
        for (Game g : Main.runningGames) {
            for (GamePlayer p : g.getPlayers()) {
                if (p.getBukkitPlayer().equals(event.getPlayer())) {
                    game = g;
                    break;
                }
            }
        }

        if (game == null) return;

        event.getPlayer().openInventory(new KitGui(event.getPlayer(), game).getBukkitInventory());

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        if (!Main.pregame.contains(event.getWhoClicked())) return;
        if (!event.getView().getTitle().equals("§6Select Kit")) return;

        event.setCancelled(true);

        Game game = null;
        for (Game g : Main.runningGames) {
            for (GamePlayer p : g.getPlayers()) {
                if (p.getBukkitPlayer().equals(event.getWhoClicked())) {
                    game = g;
                    break;
                }
            }
        }

        if (game == null) return;

        int slot = event.getSlot();

        if (Main.kits.size() <= slot+1) {
            Kit kit = new Kit(Main.kits.get(slot));
            if (event.getWhoClicked().hasPermission(kit.getPermission())) {
                game.setKit((Player) event.getWhoClicked(), kit);
                event.getWhoClicked().sendMessage("Kit selected!");
            }
        }
    }
}
