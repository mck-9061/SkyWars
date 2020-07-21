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
            } else players.clear();
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
    }
}
