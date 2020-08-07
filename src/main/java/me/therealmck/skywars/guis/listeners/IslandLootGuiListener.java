package me.therealmck.skywars.guis.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsSettings;
import me.therealmck.skywars.data.loot.LootTable;
import me.therealmck.skywars.data.loot.enums.*;
import me.therealmck.skywars.guis.customgame.EventChooserGui;
import me.therealmck.skywars.guis.customgame.IslandLootGui;
import me.therealmck.skywars.guis.customgame.MainCustomGameGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class IslandLootGuiListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();

        if (title.equals("§6Island Loot Settings")) {
            if (event.getClickedInventory() == null) return;

            event.setCancelled(true);

            Main.preventInventoryCloseList.remove((Player) event.getWhoClicked());

            Game game = Main.activeCustomGames.get(event.getWhoClicked());
            SkyWarsSettings settings = game.getSettings();
            LootTable table = settings.getIslandLootTable();

            switch (event.getSlot()) {
                case 9:
                    SwordLevel currentLevel = table.getSwordLevel();
                    SwordLevel changeTo;
                    if (currentLevel.equals(SwordLevel.NO_SWORDS)) changeTo = SwordLevel.LEVEL_1;
                    else if (currentLevel.equals(SwordLevel.LEVEL_4)) changeTo = SwordLevel.NO_SWORDS;
                    else {
                        int current = Integer.parseInt(currentLevel.name().split("_")[1]);
                        current++;
                        changeTo = SwordLevel.valueOf("LEVEL_"+current);
                    }

                    table.setSwordLevel(changeTo);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 11:
                    BowLevel currentBowLevel = table.getBowLevel();
                    BowLevel changeToBow;
                    if (currentBowLevel.equals(BowLevel.NO_BOWS)) changeToBow = BowLevel.LEVEL_1;
                    else if (currentBowLevel.equals(BowLevel.LEVEL_4)) changeToBow = BowLevel.NO_BOWS;
                    else {
                        int current = Integer.parseInt(currentBowLevel.name().split("_")[1]);
                        current++;
                        changeToBow = BowLevel.valueOf("LEVEL_"+current);
                    }

                    table.setBowLevel(changeToBow);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 12:
                    PearlLevel currentPearlLevel = table.getPearlLevel();
                    PearlLevel changeToPearl;
                    if (currentPearlLevel.equals(PearlLevel.NO_PEARLS)) changeToPearl = PearlLevel.LEVEL_1;
                    else if (currentPearlLevel.equals(PearlLevel.LEVEL_4)) changeToPearl = PearlLevel.NO_PEARLS;
                    else {
                        int current = Integer.parseInt(currentPearlLevel.name().split("_")[1]);
                        current++;
                        changeToPearl = PearlLevel.valueOf("LEVEL_"+current);
                    }

                    table.setPearlLevel(changeToPearl);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 14:
                    ProjectileLevel currentProjectileLevel = table.getProjectileLevel();
                    ProjectileLevel changeToProjectile;
                    if (currentProjectileLevel.equals(ProjectileLevel.NO_PROJECTILES)) changeToProjectile = ProjectileLevel.LEVEL_1;
                    else if (currentProjectileLevel.equals(ProjectileLevel.LEVEL_4)) changeToProjectile = ProjectileLevel.NO_PROJECTILES;
                    else {
                        int current = Integer.parseInt(currentProjectileLevel.name().split("_")[1]);
                        current++;
                        changeToProjectile = ProjectileLevel.valueOf("LEVEL_"+current);
                    }

                    table.setProjectileLevel(changeToProjectile);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());
                    break;
                case 15:
                    ArmorLevel currentArmorLevel = table.getArmorLevel();
                    ArmorLevel changeToArmor;
                    if (currentArmorLevel.equals(ArmorLevel.NO_ARMOR)) changeToArmor = ArmorLevel.LEVEL_1;
                    else if (currentArmorLevel.equals(ArmorLevel.LEVEL_4)) changeToArmor = ArmorLevel.NO_ARMOR;
                    else {
                        int current = Integer.parseInt(currentArmorLevel.name().split("_")[1]);
                        current++;
                        changeToArmor = ArmorLevel.valueOf("LEVEL_"+current);
                    }

                    table.setArmorLevel(changeToArmor);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 17:
                    MiscLevel currentMiscLevel = table.getMiscLevel();
                    MiscLevel changeToMisc;
                    if (currentMiscLevel.equals(MiscLevel.NO_MISC)) changeToMisc = MiscLevel.LEVEL_1;
                    else if (currentMiscLevel.equals(MiscLevel.LEVEL_4)) changeToMisc = MiscLevel.NO_MISC;
                    else {
                        int current = Integer.parseInt(currentMiscLevel.name().split("_")[1]);
                        current++;
                        changeToMisc = MiscLevel.valueOf("LEVEL_"+current);
                    }

                    table.setMiscLevel(changeToMisc);
                    settings.setIslandLootTable(table);
                    game.setSettings(settings);
                    Main.activeCustomGames.remove(event.getWhoClicked());
                    Main.activeCustomGames.put((Player) event.getWhoClicked(), game);

                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new IslandLootGui((Player) event.getWhoClicked(), game)).getBukkitInventory());

                    break;
                case 26:
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().openInventory((new MainCustomGameGui((Player) event.getWhoClicked(), game)).getBukkitInventory());
                    break;
            }

        }
    }
}
