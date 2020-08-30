package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Kit;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.customgame.MainCustomGameGui;
import me.therealmck.skywars.utils.MessageHelper;
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
        MessageHelper lang = new MessageHelper();
        String sub = "";
        try { sub = args[0]; }
        catch (Exception e) { commandSender.sendMessage(lang.getCommandFailed()); }

        switch (sub) {
            case "help":
                for (String help : lang.getHelpMessages()) commandSender.sendMessage(help);

                if (commandSender.hasPermission("skywars.admin")) {
                    for (String help : lang.getAdminHelpMessages()) commandSender.sendMessage(help);
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
                            gp.getBukkitPlayer().sendMessage(lang.getJoinedGame(commandSender.getName())
                                    + " (" + game.getPlayers().size() + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")");
                    } else commandSender.sendMessage(lang.getGameFull());
                } catch (Exception e) {
                    commandSender.sendMessage(lang.getCustomGameJoinPlayerUnspecified());
                }

                break;
            case "kit":
                if (commandSender.hasPermission("skywars.admin") && commandSender instanceof Player) {
                    List<ItemStack> items = new ArrayList<>();

                    for (ItemStack item : ((Player) commandSender).getInventory()) items.add(item.clone());

                    try {
                        for (Kit kit : Main.kits) {
                            if (kit.getName().equals(args[0])) {
                                commandSender.sendMessage(lang.getKitAlreadyExists());
                                return true;
                            }
                        }

                        if (args[1] == null) {
                            commandSender.sendMessage(lang.getInvalidSyntax());
                            return false;
                        }
                        ConfigurationSection section = Main.kitsConfig.createSection(args[0]);
                        section.set("permission", args[1]);
                        section.set("icon", ((Player) commandSender).getInventory().getItemInMainHand().clone());
                        section.set("items", items);

                        Main.saveKitsConfig();

                        Main.kits.add(new Kit(args[0]));
                        commandSender.sendMessage(lang.getKitCreated());
                    } catch (Exception e) {
                        commandSender.sendMessage(lang.getInvalidSyntax());
                        return false;
                    }
                } else {
                    commandSender.sendMessage(lang.getUnauthorized());
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
            case "forcestart":
                if (commandSender instanceof Player && commandSender.hasPermission("skywars.admin")) {
                    Game toStart = null;
                    for (Game game : Main.waitingGames) {
                        for (GamePlayer p : game.getPlayers()) {
                            if (p.getBukkitPlayer().equals(commandSender)) toStart = game;
                        }
                    }

                    if (toStart == null) {
                        commandSender.sendMessage(lang.getForcestartFailed());
                        return true;
                    }

                    Main.waitingGames.remove(toStart);
                    Main.runningGames.add(toStart);
                    toStart.fillChests();
                    toStart.beginGame();
                }
            case "leave":
                if (commandSender instanceof Player) {
                    Game toLeave = null;
                    GamePlayer player = null;
                    for (Game game : Main.waitingGames) {
                        for (GamePlayer p : game.getPlayers()) {
                            if (p.getBukkitPlayer().equals(commandSender)) {
                                toLeave = game;
                                player = p;
                            }
                        }
                    }

                    if (toLeave == null) {
                        commandSender.sendMessage(lang.getLeaveFailed());
                        return true;
                    }

                    toLeave.removePlayer(player);

                    for (GamePlayer p : toLeave.getPlayers()) {
                        p.getBukkitPlayer().sendMessage(lang.getLeftGame(commandSender.getName()));
                    }

                    player.getBukkitPlayer().teleport(Main.skyWarsConfig.getLocation("LobbyLocation"));


                } else {
                    commandSender.sendMessage(lang.getUnauthorized());
                }
        }
        return true;
    }
}
