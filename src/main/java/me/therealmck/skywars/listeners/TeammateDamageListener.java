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
            for (GamePlayer gp : game.getPlayers()) players.add(gp.getBukkitPlayer());
            if (players.contains((Player) event.getDamager()) && players.contains((Player) event.getEntity())) {
                game = g;
                break;
            }
            else players.clear();
        }

        Team team = null;
        for (Team t : game.getTeams()) {
            if (t.getPlayer1().getBukkitPlayer().equals(event.getDamager()) || t.getPlayer2().getBukkitPlayer().equals(event.getDamager())) {
                team = t;
                break;
            }
        }

        if (team == null) return;

        if ((team.getPlayer1().getBukkitPlayer().equals(event.getDamager()) && team.getPlayer2().getBukkitPlayer().equals(event.getEntity()))
        || (team.getPlayer2().getBukkitPlayer().equals(event.getDamager()) && team.getPlayer1().getBukkitPlayer().equals(event.getEntity()))) {
            event.setCancelled(true);
        }


    }
}
