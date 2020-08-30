package me.therealmck.skywars.data

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.players.GamePlayer
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class Queue {
    private val regularQueue: MutableList<Player>
    private val fastPassQueue: MutableList<Player>
    private val customGameQueue: MutableList<Game>
    private val reminder: BukkitRunnable
    fun getRegularQueue(): List<Player> {
        return regularQueue
    }

    fun getFastPassQueue(): List<Player> {
        return fastPassQueue
    }

    fun getCustomGameQueue(): List<Game> {
        return customGameQueue
    }

    fun addRegularPlayer(player: Player) {
        regularQueue.add(player)
    }

    fun addFastPlayer(player: Player) {
        fastPassQueue.add(player)
    }

    fun addGame(game: Game) {
        customGameQueue.add(game)
    }

    fun processQueue(oldGame: Game) {
        // Logic to add queued players and games to a running game.
        if (!customGameQueue.isEmpty()) {
            // Add a queued custom game to a running game.
            val game = customGameQueue[0]
            customGameQueue.remove(game)
            game.setMap(oldGame.map!!)
            game.fillChests()
            game.beginGame()
            Main.waitingGames.remove(oldGame)
            Main.runningGames.add(game)
        } else {
            // Process fast pass queue before regular queue
            val game = Game()
            game.setMap(oldGame.map!!)
            val maxPlayers = Main.skyWarsConfig.getInt("MaximumPlayers")
            val fastPassRemove: MutableList<Player> = ArrayList()
            val regularRemove: MutableList<Player> = ArrayList()
            for (p in fastPassQueue) {
                if (game.getPlayers().size < maxPlayers) {
                    game.addPlayer(GamePlayer(p))
                    fastPassRemove.add(p)
                    for (gp in game.getPlayers()) gp.bukkitPlayer.sendMessage(p.displayName
                            + " joined the game! (" + game.getPlayers().size + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")")
                }
            }
            for (p in regularQueue) {
                if (game.getPlayers().size < maxPlayers) {
                    game.addPlayer(GamePlayer(p))
                    regularRemove.add(p)
                    for (gp in game.getPlayers()) gp.bukkitPlayer.sendMessage(p.displayName
                            + " joined the game! (" + game.getPlayers().size + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")")
                }
            }
            fastPassQueue.removeAll(fastPassRemove)
            regularQueue.removeAll(regularRemove)
            Main.waitingGames.remove(oldGame)
            Main.waitingGames.add(game)
        }
    }

    init {
        regularQueue = ArrayList()
        fastPassQueue = ArrayList()
        customGameQueue = ArrayList()
        reminder = object : BukkitRunnable() {
            override fun run() {
                for (p in regularQueue) p.sendMessage("Postion " + regularQueue.indexOf(p) + " in the queue.")
                for (p in fastPassQueue) p.sendMessage("Postion " + fastPassQueue.indexOf(p) + " in the Fast Pass queue.")
                for (game in customGameQueue) {
                    for (gp in game.getPlayers()) gp.bukkitPlayer.sendMessage("Postion " + customGameQueue.indexOf(game) + " in the Custom Game queue.")
                }
            }
        }
        reminder.runTaskTimer(Main.instance, 0, 200)
    }
}