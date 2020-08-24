package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsSettings;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.customgame.*;
import me.therealmck.skywars.utils.MessageHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventChooserGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        MessageHelper lang = new MessageHelper();
        String title = event.getView().getTitle();

        if (title.equals(lang.getCustomGameEventGui())) {
            if (event.getClickedInventory() == null) return;

            event.setCancelled(true);

            Main.preventInventoryCloseList.remove((Player) event.getWhoClicked());

            Game game = Main.activeCustomGames.get(event.getWhoClicked());
            SkyWarsSettings settings = game.getSettings();

            switch (event.getSlot()) {
                case 10:
                    settings.setAnvilRainEvent(!settings.isAnvilRainEvent());
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);


                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new EventChooserGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 12:
                    settings.setBlockDecayEvent(!settings.isBlockDecayEvent());
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new EventChooserGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 14:
                    settings.setHorseMountEvent(!settings.isHorseMountEvent());
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new EventChooserGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 16:
                    settings.setZombieHordeEvent(!settings.isZombieHordeEvent());
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new EventChooserGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 26:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new MainCustomGameGui((Player) event.getWhoClicked(), game)).getBukkitInventory());
                    break;
            }
        }
    }
}
