package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.Team;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class TeammateDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Game game = null;
        List<Player> players = new ArrayList<>();

        for (Game g : Main.runningGames) {
            for (GamePlayer gp : g.getPlayers()) players.add(gp.getBukkitPlayer());
            if (players.contains((Player) event.getDamager()) && players.contains((Player) event.getEntity())) {
                game = g;
                break;
            }
            else players.clear();
        }

        if (game == null) return;

        Team team = null;
        for (Team t : game.getTeams()) {
            for (GamePlayer player : t.getPlayers()) {
                if (player.getBukkitPlayer().getUniqueId().equals(event.getDamager().getUniqueId())) {
                    team = t;
                    break;
                }
            }
        }

        if (team == null) return;

        for (GamePlayer player : team.getPlayers()) {
            if (player.getBukkitPlayer().getUniqueId().equals(event.getEntity().getUniqueId())) event.setCancelled(true);
        }
    }
}
