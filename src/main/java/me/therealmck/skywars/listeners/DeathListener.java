package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.SkyWarsSettings;
import me.therealmck.skywars.data.Team;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
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

            killed.getBukkitPlayer().setMaxHealth(20);
            killed.getBukkitPlayer().setWalkSpeed((float) (0.2));
            for (PotionEffect effect : killed.getBukkitPlayer().getActivePotionEffects()) killed.getBukkitPlayer().removePotionEffect(effect.getType());

            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().setHealth(20);
            event.getEntity().sendTitle("§c§lYOU DIED!", "§cRun /skywars lobby to return.", 0, 80, 0);
            event.getEntity().teleport(game.getMap().getSpawns().get(0));


            // Check if a team has won
            List<Team> aliveTeams = new ArrayList<>();
            for (Team team : game.getTeams()) {
                if ((!team.getPlayer1().isDead() && game.getPlayers().contains(team.getPlayer1()))
                        || (!team.getPlayer2().isDead() && game.getPlayers().contains(team.getPlayer2()))) aliveTeams.add(team);
            }

            if (aliveTeams.size() == 1) {
                Team won = aliveTeams.get(0);

                won.getPlayer1().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer1().getBukkitPlayer().setHealth(20);
                won.getPlayer1().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!game.isCustom()) won.getPlayer1().saveStats(true);
                won.getPlayer1().getBukkitPlayer().setMaxHealth(20);
                won.getPlayer1().getBukkitPlayer().setWalkSpeed((float) (0.2));
                for (PotionEffect effect : won.getPlayer1().getBukkitPlayer().getActivePotionEffects()) won.getPlayer1().getBukkitPlayer().removePotionEffect(effect.getType());

                won.getPlayer2().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
                won.getPlayer2().getBukkitPlayer().setHealth(20);
                won.getPlayer2().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
                if (!game.isCustom()) won.getPlayer2().saveStats(true);
                won.getPlayer2().getBukkitPlayer().setMaxHealth(20);
                won.getPlayer2().getBukkitPlayer().setWalkSpeed((float) (0.2));
                for (PotionEffect effect : won.getPlayer2().getBukkitPlayer().getActivePotionEffects()) won.getPlayer2().getBukkitPlayer().removePotionEffect(effect.getType());

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
                game.cancelTasks();
                game.wipePlayersWithDelay(120);
            }
        }
    }
}
