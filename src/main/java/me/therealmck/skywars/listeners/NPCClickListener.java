package me.therealmck.skywars.listeners;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.Game;
import me.therealmck.skywars.data.players.GamePlayer;
import me.therealmck.skywars.utils.MessageHelper;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCClickListener implements Listener {
    @EventHandler
    public void onNpcClick(NPCRightClickEvent event) {
        MessageHelper lang = new MessageHelper();
        if (event.getNPC().getName().equals("SkyWars")) {
            // Let the player join a game
            Game toJoin = null;

            for (Game game : Main.waitingGames) {
                if (game.getPlayers().size() < Main.skyWarsConfig.getInt("MaximumPlayers")) {
                    toJoin = game;
                }

                for (GamePlayer p : game.getPlayers()) {
                    if (p.getBukkitPlayer().equals(event.getClicker())) return;
                }
            }

            for (Game game : Main.runningGames) {
                for (GamePlayer p : game.getPlayers()) {
                    if (p.getBukkitPlayer().equals(event.getClicker())) return;
                }
            }

            if (toJoin != null) {
                toJoin.addPlayer(new GamePlayer(event.getClicker()));

                for (GamePlayer gp : toJoin.getPlayers()) gp.getBukkitPlayer().sendMessage(lang.getJoinedGame(event.getClicker().getDisplayName()) + "(" + toJoin.getPlayers().size() + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")");
                event.getClicker().teleport(toJoin.getMap().getLobby());
            }
            else {
                event.getClicker().sendMessage(lang.getNoGamesAvailable());

                if (event.getClicker().hasPermission("skywars.fastpass")) Main.queue.addFastPlayer(event.getClicker());
                else Main.queue.addRegularPlayer(event.getClicker());
            }
        }
    }
}
