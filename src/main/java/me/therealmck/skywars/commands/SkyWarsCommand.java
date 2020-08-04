package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.customgame.MainCustomGameGui;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SkyWarsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String sub = "";
        try { sub = args[0]; }
        catch (Exception e) { commandSender.sendMessage("Unknown subcommand. Run [/skywars help] for help."); }

        switch (sub) {
            case "help":
                commandSender.sendMessage("§6/skywars help§b: Display this help menu");
                commandSender.sendMessage("§6/skywars create§b: Create a custom game of SkyWars");
                commandSender.sendMessage("§6/skywars join [IGN of host]§b: Join the specified host's game of SkyWars");
                commandSender.sendMessage("§6/skywars lobby§b: Teleport to the SkyWars lobby");

                if (commandSender.hasPermission("skywars.admin")) {
                    commandSender.sendMessage("§6/skywars kit [name] [permission]§b: Create a SkyWars kit out of your inventory. Hold the item for the icon in your main hand.");
                }

                break;
            case "create":
                if (commandSender instanceof Player) {
                    for (Game game : Main.waitingGames) if (game.getPlayers().contains(commandSender)) return true;
                    for (Game game : Main.runningGames) if (game.getPlayers().contains(commandSender)) return true;
                    for (Game game : Main.activeCustomGames.values()) {
                        if (Main.activeCustomGames.get(commandSender).equals(game)) {
                            ((Player) commandSender).openInventory(new MainCustomGameGui((Player) commandSender, game).getBukkitInventory());
                            return true;
                        }
                    }

                    Game createdGame = new Game();
                    createdGame.setCustom(true);
                    createdGame.addPlayer(new GamePlayer((Player) commandSender));
                    Main.activeCustomGames.put((Player) commandSender, createdGame);

                    MainCustomGameGui gui = new MainCustomGameGui((Player) commandSender, createdGame);
                    ((Player) commandSender).openInventory(gui.getBukkitInventory());

                }

                break;
            case "join":
                if (!(commandSender instanceof Player)) return false;
                try {
                    for (Game game : Main.waitingGames) if (game.getPlayers().contains(commandSender)) return true;
                    for (Game game : Main.runningGames) if (game.getPlayers().contains(commandSender)) return true;
                    for (Game game : Main.activeCustomGames.values()) if (game.getPlayers().contains(commandSender)) return true;
                    Player p = Bukkit.getPlayer(args[1]);
                    Game game = Main.activeCustomGames.get(p);

                    if (game.getPlayers().size() < Main.skyWarsConfig.getInt("MaximumPlayers")) {
                        game.addPlayer(new GamePlayer((Player) commandSender));
                        for (GamePlayer gp : game.getPlayers())
                            gp.getBukkitPlayer().sendMessage(commandSender.getName()
                                    + " joined the game! (" + game.getPlayers().size() + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")");
                    } else commandSender.sendMessage("This game is full.");
                } catch (Exception e) {
                    commandSender.sendMessage("You didn't specify a player or that player isn't hosting a game.");
                }

                break;
            case "kit":
                if (commandSender.hasPermission("skywars.admin") && commandSender instanceof Player) {
                    List<ItemStack> items = new ArrayList<>();

                    for (ItemStack item : ((Player) commandSender).getInventory()) items.add(item.clone());

                    try {
                        for (Kit kit : Main.kits) {
                            if (kit.getName().equals(args[0])) {
                                commandSender.sendMessage("A kit with that name already exists.");
                                return true;
                            }
                        }

                        if (args[1] == null) {
                            commandSender.sendMessage("Invalid syntax!");
                            return false;
                        }
                        ConfigurationSection section = Main.kitsConfig.createSection(args[0]);
                        section.set("permission", args[1]);
                        section.set("icon", ((Player) commandSender).getInventory().getItemInMainHand().clone());
                        section.set("items", items);

                        Main.saveKitsConfig();

                        Main.kits.add(new Kit(args[0]));
                        commandSender.sendMessage("Added kit successfully.");
                    } catch (Exception e) {
                        commandSender.sendMessage("Invalid syntax!");
                        return false;
                    }
                } else {
                    commandSender.sendMessage("Something went wrong. You may not have permission or you may not be a player.");
                }

                break;
            case "lobby":
                if (commandSender instanceof Player) {
                    Location lobby = Main.skyWarsConfig.getLocation("LobbyLocation");

                    boolean shouldTp = true;
                    Game leavingGame = null;
                    GamePlayer gp = null;
                    for (Game game : Main.runningGames) {
                        for (GamePlayer player : game.getPlayers()) {
                            if (player.getBukkitPlayer().equals(commandSender) && !(((Player) commandSender)).getGameMode().equals(GameMode.SPECTATOR)) {
                                shouldTp = false;
                            } else if (player.getBukkitPlayer().equals(commandSender) && ((Player) commandSender).getGameMode().equals(GameMode.SPECTATOR)) {
                                leavingGame = game;
                                gp = player;
                            }
                        }
                    }

                    if (shouldTp) {
                        ((Player) commandSender).teleport(lobby);
                        if (leavingGame != null) {
                            ((Player) commandSender).setMaxHealth(20);
                            ((Player) commandSender).setWalkSpeed((float) 0.2);
                            for (PotionEffect effect : ((Player) commandSender).getActivePotionEffects()) ((Player) commandSender).removePotionEffect(effect.getType());

                            leavingGame.removePlayer(gp);
                            ((Player) commandSender).setGameMode(GameMode.SURVIVAL);
                        }
                    }
                }
                break;
        }
        return true;
    }
}
