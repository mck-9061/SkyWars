package me.therealmck.skywars.data;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.players.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private List<Player> regularQueue;
    private List<Player> fastPassQueue;
    private List<Game> customGameQueue;
    private BukkitRunnable reminder;

    public Queue() {
        this.regularQueue = new ArrayList<>();
        this.fastPassQueue = new ArrayList<>();
        this.customGameQueue = new ArrayList<>();

        reminder = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : regularQueue) p.sendMessage("Postion "+regularQueue.indexOf(p)+" in the queue.");
                for (Player p : fastPassQueue) p.sendMessage("Postion "+fastPassQueue.indexOf(p)+" in the Fast Pass queue.");
                for (Game game : customGameQueue) {
                    for (GamePlayer gp : game.getPlayers()) gp.getBukkitPlayer().sendMessage("Postion "+customGameQueue.indexOf(game)+" in the Custom Game queue.");
                }
            }
        };

        reminder.runTaskTimer(Main.instance, 0, 200);
    }

    public List<Player> getRegularQueue() {
        return regularQueue;
    }

    public List<Player> getFastPassQueue() {
        return fastPassQueue;
    }

    public List<Game> getCustomGameQueue() {
        return customGameQueue;
    }

    public void addRegularPlayer(Player player) {
        regularQueue.add(player);
    }

    public void addFastPlayer(Player player) {
        fastPassQueue.add(player);
    }

    public void addGame(Game game) {
        customGameQueue.add(game);
    }

    public void processQueue(Game oldGame) {
        // Logic to add queued players and games to a running game.
        if (!customGameQueue.isEmpty()) {
            // Add a queued custom game to a running game.
            Game game = customGameQueue.get(0);
            customGameQueue.remove(game);

            game.setMap(oldGame.getMap());
            game.fillChests();
            game.beginGame();
            Main.waitingGames.remove(oldGame);
            Main.runningGames.add(game);

        } else {
            // Process fast pass queue before regular queue
            Game game = new Game();
            game.setMap(oldGame.getMap());
            int maxPlayers = Main.skyWarsConfig.getInt("MaximumPlayers");

            List<Player> fastPassRemove = new ArrayList<>();
            List<Player> regularRemove = new ArrayList<>();

            for (Player p : fastPassQueue) {
                if (game.getPlayers().size() < maxPlayers) {
                    game.addPlayer(new GamePlayer(p));
                    fastPassRemove.add(p);

                    for (GamePlayer gp : game.getPlayers()) gp.getBukkitPlayer().sendMessage(p.getDisplayName()
                            + " joined the game! (" + game.getPlayers().size() + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")");


                }
            }

            for (Player p : regularQueue) {
                if (game.getPlayers().size() < maxPlayers) {
                    game.addPlayer(new GamePlayer(p));
                    regularRemove.add(p);

                    for (GamePlayer gp : game.getPlayers()) gp.getBukkitPlayer().sendMessage(p.getDisplayName()
                            + " joined the game! (" + game.getPlayers().size() + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")");


                }
            }

            fastPassQueue.removeAll(fastPassRemove);
            regularQueue.removeAll(regularRemove);

            Main.waitingGames.remove(oldGame);
            Main.waitingGames.add(game);

        }
    }
}
