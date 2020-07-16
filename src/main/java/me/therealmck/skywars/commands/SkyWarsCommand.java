package me.therealmck.skywars.commands;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.guis.customgame.MainCustomGameGui;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
                commandSender.sendMessage("§6/skywars kit§b: Pick the kit you use for SkyWars games");
                commandSender.sendMessage("§6/skywars lobby§b: Teleport to the SkyWars lobby");

                break;
            case "create":
                if (commandSender instanceof Player) {
                    Game createdGame = new Game();
                    createdGame.setCustom(true);
                    createdGame.addPlayer(new GamePlayer((Player) commandSender));
                    Main.activeCustomGames.put((Player) commandSender, createdGame);

                    MainCustomGameGui gui = new MainCustomGameGui((Player) commandSender, createdGame);
                    ((Player) commandSender).openInventory(gui.getBukkitInventory());

                }

                break;
            case "join":


                break;
            case "kit":
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
