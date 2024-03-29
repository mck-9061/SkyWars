package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsMap;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.customgame.EventChooserGui;
import me.therealmck.skywars.guis.customgame.IslandLootGui;
import me.therealmck.skywars.guis.customgame.MidLootGui;
import me.therealmck.skywars.guis.customgame.ModifierGui;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainCustomGameGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        MessageHelper lang = new MessageHelper();
        String title = event.getView().getTitle();

        if (title.equals(lang.getCustomGameMainGui())) {
            if (event.getClickedInventory() == null) return;

            event.setCancelled(true);
            Main.preventInventoryCloseList.remove(event.getWhoClicked());

            switch (event.getSlot()) {
                case 10:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), Main.activeCustomGames.get(event.getWhoClicked()))).getBukkitInventory());
                    break;
                case 12:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new MidLootGui((Player) event.getWhoClicked(), Main.activeCustomGames.get(event.getWhoClicked()))).getBukkitInventory());
                    break;
                case 14:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new ModifierGui((Player) event.getWhoClicked(), Main.activeCustomGames.get(event.getWhoClicked()))).getBukkitInventory());
                    break;
                case 16:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new EventChooserGui((Player) event.getWhoClicked(), Main.activeCustomGames.get(event.getWhoClicked()))).getBukkitInventory());
                    break;
                case 18:
                    List<Game> games = new ArrayList<>();
                    Game activeGame = Main.activeCustomGames.get(event.getWhoClicked());

                    if (activeGame.getPlayers().size() == 1) {
                        event.getWhoClicked().sendMessage(lang.getNotEnoughInCustomGame(event.getWhoClicked().getName()));
                        return;
                    }

                    for (Game game : Main.waitingGames) {
                        if (game.getPlayers().isEmpty()) games.add(game);
                    }

                    Main.preventInventoryCloseList.remove(event.getWhoClicked());
                    event.getWhoClicked().closeInventory();


                    if (games.isEmpty()) {
                        for (GamePlayer p : activeGame.getPlayers()) {
                            p.getBukkitPlayer().sendMessage(lang.getNoMapsForCustomGame());
                        }
                        Main.queue.addGame(activeGame);
                    } else {
                        Random r = new Random();
                        Game game = games.get(r.nextInt(games.size()));

                        Main.waitingGames.remove(game);
                        Main.runningGames.add(activeGame);

                        activeGame.setMap(game.getMap());
                        Bukkit.getScheduler().runTask(Main.instance, () -> {
                            activeGame.fillChests();
                            activeGame.beginGame();
                        });
                    }


                    break;
                case 26:
                    Game game = Main.activeCustomGames.get(event.getWhoClicked());
                    for (GamePlayer player : game.getPlayers()) {
                        player.getBukkitPlayer().sendMessage(lang.getRoomDeleted());
                    }
                    game.wipePlayers();
                    Main.preventInventoryCloseList.remove(event.getWhoClicked());
                    event.getWhoClicked().closeInventory();
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    break;
            }
        }
    }
}
