package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Team;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamPickerGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        MessageHelper lang = new MessageHelper();
        String title = event.getView().getTitle();

        if (title.equals("§6Pick your team")) {
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

            ItemStack item = event.getClickedInventory().getItem(event.getRawSlot());
            if (item == null || item.getType() == null) return;

            Team addTo = null;

            for (Team team : game.getTeams()) {
                if (team.getIcon().equals(item.getType())) {
                    int maxPlayers = Main.skyWarsConfig.getInt("MaximumPlayers");
                    int teamCount = Main.skyWarsConfig.getInt("TeamCount");
                    int teamSize = maxPlayers / teamCount;

                    int currentSize = team.getPlayers().size();

                    if (currentSize != teamSize) addTo = team;
                    else player.getBukkitPlayer().sendMessage("This team is full.");
                }
            }

            if (addTo == null) return;

            for (Team team : game.getTeams()) {
                if (team.getPlayers().contains(player)) {
                    team.removePlayer(player);
                    for (ItemStack i : event.getClickedInventory()) {
                        if (i == null || i.getType() == null) continue;
                        if (i.getType().equals(team.getIcon())) {
                            ItemMeta meta = i.getItemMeta();
                            List<String> lore;
                            if (meta.hasLore()) lore = meta.getLore();
                            else lore = new ArrayList<>();

                            if (lore.contains(player.getBukkitPlayer().getDisplayName())) lore.remove(player.getBukkitPlayer().getDisplayName());
                            meta.setLore(lore);
                            i.setItemMeta(meta);
                            break;
                        }
                    }
                }
            }


            addTo.addPlayer(player);
            player.getBukkitPlayer().sendMessage(lang.getTeamPicked());
            ItemMeta meta = item.getItemMeta();
            List<String> lore;
            if (meta.hasLore()) lore = meta.getLore();
            else lore = new ArrayList<>();

            lore.add(player.getBukkitPlayer().getDisplayName());
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
    }
}
