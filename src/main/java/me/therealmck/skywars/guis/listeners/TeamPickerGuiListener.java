package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class TeamPickerGuiListener implements Listener {
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equals("§6Pick your teammate")) {
            if (event.getClickedInventory() == null) return;

            event.setCancelled(true);

            Game game = null;
            GamePlayer player = null;

            for (Game g : Main.runningGames) {
                for (GamePlayer p : g.getPlayers()) {
                    if (p.getBukkitPlayer().equals(event.getWhoClicked())) {
                        game = g;
                        player = p;
                        break;
                    }
                }
            }

            if (game == null) return;

            game.setTeammatePref(player.getBukkitPlayer(), event.getRawSlot());
            player.getBukkitPlayer().sendMessage("Set your teammate preference! The game will start soon.");
        }
    }
}
