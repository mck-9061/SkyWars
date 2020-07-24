package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsSettings;
import me.therealmck.skywars.data.Team;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DisconnectListener implements Listener {
    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        GamePlayer gp = null;
        Game g = null;

        GamePlayer toRemove = null;
        Game toRemoveGame = null;

        for (Game game : Main.waitingGames) {
            for (GamePlayer player : game.getPlayers()) {
                if (player.getBukkitPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
                    toRemove = player;
                    toRemoveGame = game;
                    break;
                }
            }
        }

        for (Game game : Main.runningGames) {
            for (GamePlayer player : game.getPlayers()) {
                if (player.getBukkitPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
                    gp = player;
                    g = game;
                    break;
                }
            }
        }

        if (gp != null) {
            if (!g.isCustom()) gp.saveStats(false);
            g.removePlayer(gp);

            ConfigurationSection section = Main.playerDataConfig.getConfigurationSection(event.getPlayer().getUniqueId().toString());
            section.set("tp", true);
            Main.savePlayerDataConfig();

            // Check if a team has won
            List<Team> aliveTeams = new ArrayList<>();
            for (Team team : g.getTeams()) {
                if ((!team.getPlayer1().isDead() && g.getPlayers().contains(team.getPlayer1()))
                        || (!team.getPlayer2().isDead() && g.getPlayers().contains(team.getPlayer2()))) aliveTeams.add(team);
            }

            if (aliveTeams.size() == 1) {
                Team won = aliveTeams.get(0);

                won.getPlayer1().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer1().getBukkitPlayer().setHealth(20);
                won.getPlayer1().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!g.isCustom()) won.getPlayer1().saveStats(true);

                won.getPlayer2().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer2().getBukkitPlayer().setHealth(20);
                won.getPlayer2().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!g.isCustom()) won.getPlayer2().saveStats(true);

                World map = g.getMap().getBukkitWorld();

                List<Player> players = new ArrayList<>();
                for (GamePlayer p : g.getPlayers()) players.add(p.getBukkitPlayer());

                // Boot all players after 6 seconds, reset the game, then process the queue
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : players) {
                            if (map.getPlayers().contains(p)) {
                                p.setGameMode(GameMode.SURVIVAL);
                                p.teleport(Main.skyWarsConfig.getLocation("LobbyLocation"), PlayerTeleportEvent.TeleportCause.PLUGIN);
                            }
                        }
                    }
                }.runTaskLater(Main.instance, 120);
                g.wipePlayersWithDelay(120);
            }
        }

        if (toRemove != null) {
            toRemoveGame.removePlayer(toRemove);
        }
    }
}
