package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsSettings;
import me.therealmck.skywars.guis.customgame.MainCustomGameGui;
import me.therealmck.skywars.guis.customgame.MidLootGui;
import me.therealmck.skywars.guis.customgame.ModifierGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ModifierGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equals("§6Modifier Settings")) {
            if (event.getClickedInventory() == null) return;

            event.setCancelled(true);

            Main.preventInventoryCloseList.remove((Player) event.getWhoClicked());

            Game game = Main.activeCustomGames.get(event.getWhoClicked());
            SkyWarsSettings settings = game.getSettings();

            switch (event.getSlot()) {
                case 11:
                    int currentJumpLevel = settings.getJumpMultiplier();
                    if (currentJumpLevel == 4) currentJumpLevel = 1;
                    else currentJumpLevel++;

                    settings.setJumpMultiplier(currentJumpLevel);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new ModifierGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 13:
                    int currentSpeedLevel = settings.getSpeedMultiplier();
                    if (currentSpeedLevel == 4) currentSpeedLevel = 1;
                    else currentSpeedLevel++;

                    settings.setSpeedMultiplier(currentSpeedLevel);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new ModifierGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 15:
                    int currentMaxHealth = settings.getMaxHealth();
                    if (currentMaxHealth == 20) currentMaxHealth = 30;
                    else if (currentMaxHealth == 30) currentMaxHealth = 40;
                    else if (currentMaxHealth == 40) currentMaxHealth = 10;
                    else if (currentMaxHealth == 10) currentMaxHealth = 20;

                    settings.setMaxHealth(currentMaxHealth);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new ModifierGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 26:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new MainCustomGameGui((Player) event.getWhoClicked(), game)).getBukkitInventory());
                    break;
            }
        }
    }
}
