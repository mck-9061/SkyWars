package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Team;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class DeathListener implements Listener {
    // Just in case a player dies on their own (void etc.)
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        GamePlayer killed = null;
        Game game = null;

        for (Game g : Main.runningGames) {
            for (GamePlayer player : g.getPlayers()) {
                if (player.getBukkitPlayer().getUniqueId().equals(event.getEntity().getUniqueId())) {
                    killed = player;
                    game = g;
                    break;
                }
            }
        }

        if (killed != null) {
            // Save stats of killed player
            if (!game.isCustom()) killed.saveStats(false);
            killed.setDead(true);

            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().setHealth(20);
            event.getEntity().sendTitle("§c§lYOU DIED!", "§cRun /skywars lobby to return.", 0, 80, 0);
            event.getEntity().teleport(game.getMap().getSpawns().get(0));


            // Check if a team has won
            List<Team> aliveTeams = new ArrayList<>();
            for (Team team : game.getTeams()) {
                if (!team.getPlayer1().isDead() || !team.getPlayer2().isDead()) aliveTeams.add(team);
            }

            if (aliveTeams.size() == 1) {
                Team won = aliveTeams.get(0);

                won.getPlayer1().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer1().getBukkitPlayer().setHealth(20);
                won.getPlayer1().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!game.isCustom()) won.getPlayer1().saveStats(true);

                won.getPlayer2().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer2().getBukkitPlayer().setHealth(20);
                won.getPlayer2().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!game.isCustom()) won.getPlayer2().saveStats(true);

                World map = game.getMap().getBukkitWorld();

                List<Player> players = new ArrayList<>();
                for (GamePlayer p : game.getPlayers()) players.add(p.getBukkitPlayer());

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
                game.wipePlayersWithDelay(120);
                game.restoreBackup();
                Main.runningGames.remove(game);
                Main.waitingGames.add(game);
                Main.queue.processQueue(game);
            }
        }
    }
}
