package me.therealmck.skywars.commands

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.Game
import me.therealmck.skywars.data.Kit
import me.therealmck.skywars.data.players.GamePlayer
import me.therealmck.skywars.guis.customgame.MainCustomGameGui
import me.therealmck.skywars.utils.MessageHelper
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class SkyWarsCommand : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, s: String, args: Array<String>): Boolean {
        val lang = MessageHelper()
        var sub = ""
        try {
            sub = args[0]
        } catch (e: Exception) {
            commandSender.sendMessage(lang.commandFailed)
        }
        when (sub) {
            "help" -> {
                for (help in lang.helpMessages) commandSender.sendMessage(help!!)
                if (commandSender.hasPermission("skywars.admin")) {
                    for (help in lang.adminHelpMessages) commandSender.sendMessage(help!!)
                }
            }
            "create" -> if (commandSender is Player) {
                for (game in Main.waitingGames) {
                    for (p in game.getPlayers()) {
                        if (p.bukkitPlayer == commandSender) return true
                    }
                }
                for (game in Main.runningGames) if (game.getPlayers().contains(commandSender)) return true
                for (game in Main.activeCustomGames.values) {
                    if (Main.activeCustomGames[commandSender] == game) {
                        commandSender.openInventory(MainCustomGameGui(commandSender, game).bukkitInventory)
                        return true
                    }
                }
                val createdGame = Game()
                createdGame.isCustom = true
                createdGame.addPlayer(GamePlayer(commandSender))
                Main.activeCustomGames[commandSender] = createdGame
                val gui = MainCustomGameGui(commandSender, createdGame)
                commandSender.openInventory(gui.bukkitInventory)
            }
            "join" -> {
                if (commandSender !is Player) return false
                try {
                    for (game in Main.waitingGames) if (game.getPlayers().contains(commandSender)) return true
                    for (game in Main.runningGames) if (game.getPlayers().contains(commandSender)) return true
                    for (game in Main.activeCustomGames.values) if (game.getPlayers().contains(commandSender)) return true
                    val p = Bukkit.getPlayer(args[1])
                    val game = Main.activeCustomGames[p]
                    if (game!!.getPlayers().size < Main.skyWarsConfig.getInt("MaximumPlayers")) {
                        game.addPlayer(GamePlayer(commandSender))
                        for (gp in game.getPlayers()) gp.bukkitPlayer.sendMessage(lang.getJoinedGame(commandSender.getName())
                                + " (" + game.getPlayers().size + "/" + Main.skyWarsConfig.getInt("MaximumPlayers") + ")")
                    } else commandSender.sendMessage(lang.gameFull)
                } catch (e: Exception) {
                    commandSender.sendMessage(lang.customGameJoinPlayerUnspecified)
                }
            }
            "kit" -> if (commandSender.hasPermission("skywars.admin") && commandSender is Player) {
                val items: MutableList<ItemStack> = ArrayList()
                for (item in commandSender.inventory) items.add(item.clone())
                try {
                    for (kit in Main.kits) {
                        if (kit.name == args[0]) {
                            commandSender.sendMessage(lang.kitAlreadyExists)
                            return true
                        }
                    }
                    if (args[1] == null) {
                        commandSender.sendMessage(lang.invalidSyntax)
                        return false
                    }
                    val section = Main.kitsConfig.createSection(args[0])
                    section["permission"] = args[1]
                    section["icon"] = commandSender.inventory.itemInMainHand.clone()
                    section["items"] = items
                    Main.saveKitsConfig()
                    Main.kits.add(Kit(args[0]))
                    commandSender.sendMessage(lang.kitCreated)
                } catch (e: Exception) {
                    commandSender.sendMessage(lang.invalidSyntax)
                    return false
                }
            } else {
                commandSender.sendMessage(lang.unauthorized)
            }
            "lobby" -> if (commandSender is Player) {
                val lobby = Main.skyWarsConfig.getLocation("LobbyLocation")!!
                var shouldTp = true
                var leavingGame: Game? = null
                var gp: GamePlayer? = null
                for (game in Main.runningGames) {
                    for (player in game.getPlayers()) {
                        if (player.bukkitPlayer == commandSender && commandSender.gameMode != GameMode.SPECTATOR) {
                            shouldTp = false
                        } else if (player.bukkitPlayer == commandSender && commandSender.gameMode == GameMode.SPECTATOR) {
                            leavingGame = game
                            gp = player
                        }
                    }
                }
                if (shouldTp) {
                    commandSender.teleport(lobby)
                    if (leavingGame != null) {
                        commandSender.maxHealth = 20.0
                        commandSender.walkSpeed = 0.2.toFloat()
                        for (effect in commandSender.activePotionEffects) commandSender.removePotionEffect(effect.type)
                        leavingGame.removePlayer(gp)
                        commandSender.gameMode = GameMode.SURVIVAL
                    }
                }
            }
            "forcestart" -> {
                if (commandSender is Player && commandSender.hasPermission("skywars.admin")) {
                    var toStart: Game? = null
                    for (game in Main.waitingGames) {
                        for (p in game.getPlayers()) {
                            if (p.bukkitPlayer == commandSender) toStart = game
                        }
                    }
                    if (toStart == null) {
                        commandSender.sendMessage(lang.forcestartFailed)
                        return true
                    }
                    Main.waitingGames.remove(toStart)
                    Main.runningGames.add(toStart)
                    toStart.fillChests()
                    toStart.beginGame()
                }
                if (commandSender is Player) {
                    var toLeave: Game? = null
                    var player: GamePlayer? = null
                    for (game in Main.waitingGames) {
                        for (p in game.getPlayers()) {
                            if (p.bukkitPlayer == commandSender) {
                                toLeave = game
                                player = p
                            }
                        }
                    }
                    if (toLeave == null) {
                        commandSender.sendMessage(lang.leaveFailed)
                        return true
                    }
                    toLeave.removePlayer(player)
                    for (p in toLeave.getPlayers()) {
                        p.bukkitPlayer.sendMessage(lang.getLeftGame(commandSender.getName()))
                    }
                    player!!.bukkitPlayer.teleport(Main.skyWarsConfig.getLocation("LobbyLocation")!!)
                } else {
                    commandSender.sendMessage(lang.unauthorized)
                }
            }
            "leave" -> if (commandSender is Player) {
                var toLeave: Game? = null
                var player: GamePlayer? = null
                for (game in Main.waitingGames) {
                    for (p in game.getPlayers()) {
                        if (p.bukkitPlayer == commandSender) {
                            toLeave = game
                            player = p
                        }
                    }
                }
                if (toLeave == null) {
                    commandSender.sendMessage(lang.leaveFailed)
                    return true
                }
                toLeave.removePlayer(player)
                for (p in toLeave.getPlayers()) {
                    p.bukkitPlayer.sendMessage(lang.getLeftGame(commandSender.getName()))
                }
                player!!.bukkitPlayer.teleport(Main.skyWarsConfig.getLocation("LobbyLocation")!!)
            } else {
                commandSender.sendMessage(lang.unauthorized)
            }
        }
        return true
    }
}