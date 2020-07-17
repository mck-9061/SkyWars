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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class KillListener implements Listener {
    // This event listener serves two purposes:
    // Increase the kill count of the killer, and make the dead player a spectator.

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        if (!event.getEntity().isDead() || ((Player) event.getEntity()).getHealth() != 0) return;

        Game game = null;
        List<Player> players = new ArrayList<>();

        for (Game g : Main.runningGames) {
            for (GamePlayer gp : game.getPlayers()) players.add(gp.getBukkitPlayer());
            if (players.contains((Player) event.getDamager()) && players.contains((Player) event.getEntity())) {
                game = g;
                break;
            }
            else players.clear();
        }

        if (game == null) return;

        GamePlayer killer = null;
        GamePlayer killed = null;

        for (GamePlayer p : game.getPlayers()) {
            if (p.getBukkitPlayer().equals(event.getDamager())) killer = p;
            if (p.getBukkitPlayer().equals(event.getEntity())) killed = p;
        }

        if (killer == null || killed == null) return;

        killer.addKill();

        // Save stats of killed player
        if (!game.isCustom()) killed.saveStats(false);
        killed.setDead(true);

        event.setCancelled(true);
        ((Player) event.getEntity()).setGameMode(GameMode.SPECTATOR);
        ((Player) event.getEntity()).setHealth(20);
        ((Player) event.getEntity()).sendTitle("§c§lYOU DIED!", "§cRun /skywars lobby to return.", 0, 80, 0);

        // Check if killer has won
        boolean won = true;
        Team team = game.getTeam(killer);
        for (GamePlayer gp : game.getPlayers()) {
            if (gp.equals(killer)) continue;

            if (gp.getBukkitPlayer().getGameMode().equals(GameMode.SURVIVAL) && !team.containsPlayer(gp)) won = false;
        }

        if (won) {

            team.getPlayer1().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
            team.getPlayer1().getBukkitPlayer().setHealth(20);
            team.getPlayer1().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
            if (!game.isCustom()) team.getPlayer1().saveStats(true);

            team.getPlayer2().getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
            team.getPlayer2().getBukkitPlayer().setHealth(20);
            team.getPlayer2().getBukkitPlayer().sendTitle("§6§lVICTORY!", "§6Run /skywars lobby to return.", 0, 120, 0);
            if (!game.isCustom()) team.getPlayer2().saveStats(true);

            World map = game.getMap().getBukkitWorld();


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
