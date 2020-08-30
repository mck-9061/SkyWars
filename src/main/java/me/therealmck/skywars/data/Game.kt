package me.therealmck.skywars.data

import me.therealmck.skywars.Main
import me.therealmck.skywars.data.players.GamePlayer
import me.therealmck.skywars.guis.TeamPickerGui
import me.therealmck.skywars.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.WorldCreator
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.entity.EntityType
import org.bukkit.entity.Horse
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.util.*

class Game {
    var map: SkyWarsMap? = null
        private set
    private var players: MutableList<GamePlayer>
    var settings: SkyWarsSettings
    private var teams: MutableList<Team>
    var isCustom = false
    private var teamPickerGui: TeamPickerGui? = null
    private var kits = HashMap<Player, Kit?>()
    private var tasks: MutableList<BukkitRunnable> = ArrayList()
    fun fillChests() {
        val islandTable = settings.islandLootTable
        val midTable = settings.midLootTable
        val islandChests = map!!.islandChests
        val midChests = map!!.midChests
        for (islandChestLoc in islandChests) {
            islandChestLoc.world = Bukkit.getWorld(map!!.bukkitWorld.name)
            val islandChest = islandChestLoc.block
            if (islandChest.type == Material.CHEST) {
                val inv = (islandChest.state as Chest).blockInventory
                val r = Random()
                for (i in 0 until islandTable.swordLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.swordLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.swordLoot.items[r.nextInt(islandTable.swordLoot.items.size)])
                }
                for (i in 0 until islandTable.bowLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.bowLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.bowLoot.items[r.nextInt(islandTable.bowLoot.items.size)])
                }
                for (i in 0 until islandTable.pearlLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.pearlLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.pearlLoot.items[r.nextInt(islandTable.pearlLoot.items.size)])
                }
                for (i in 0 until islandTable.projectileLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.projectileLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.projectileLoot.items[r.nextInt(islandTable.projectileLoot.items.size)])
                }
                for (i in 0 until islandTable.armorLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.armorLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.armorLoot.items[r.nextInt(islandTable.armorLoot.items.size)])
                }
                for (i in 0 until islandTable.miscLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= islandTable.miscLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), islandTable.miscLoot.items[r.nextInt(islandTable.miscLoot.items.size)])
                }
            }
        }
        for (midChestLoc in midChests) {
            midChestLoc.world = Bukkit.getWorld(map!!.bukkitWorld.name)
            val midChest = midChestLoc.block
            if (midChest.type == Material.CHEST) {
                val inv = (midChest.state as Chest).blockInventory
                val r = Random()
                for (i in 0 until midTable.swordLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.swordLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.swordLoot.items[r.nextInt(midTable.swordLoot.items.size)])
                }
                for (i in 0 until midTable.bowLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.bowLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.bowLoot.items[r.nextInt(midTable.bowLoot.items.size)])
                }
                for (i in 0 until midTable.pearlLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.pearlLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.pearlLoot.items[r.nextInt(midTable.pearlLoot.items.size)])
                }
                for (i in 0 until midTable.projectileLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.projectileLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.projectileLoot.items[r.nextInt(midTable.projectileLoot.items.size)])
                }
                for (i in 0 until midTable.armorLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.armorLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.armorLoot.items[r.nextInt(midTable.armorLoot.items.size)])
                }
                for (i in 0 until midTable.miscLoot.rolls) {
                    val shouldFill = r.nextInt(100) <= midTable.miscLoot.chance
                    if (shouldFill) inv.setItem(r.nextInt(inv.size), midTable.miscLoot.items[r.nextInt(midTable.miscLoot.items.size)])
                }
            }
        }
    }

    fun setMap(map: SkyWarsMap) {
        val world = map.bukkitWorld
        // Copy world to backup
        Utils.unloadWorld(world.name + "_back")
        Utils.copyFileStructure(world.worldFolder, File(Bukkit.getWorldContainer(), world.name + "_back"))
        WorldCreator(world.name + "_back").createWorld()
        this.map = map
    }

    fun restoreBackup() {
        val world = map!!.bukkitWorld
        val worldName = world.name
        val backupWorld = Bukkit.getWorld(world.name + "_back")
        Utils.unloadWorld(worldName)
        Utils.copyFileStructure(backupWorld!!.worldFolder, File(Bukkit.getWorldContainer(), worldName))
        val newWorld = WorldCreator(worldName).createWorld()

        // Reload spawn/chest locations with new World object
        Main.instance.createSkyWarsConfig()
        map = SkyWarsMap(newWorld!!.name)
    }

    fun getPlayers(): List<GamePlayer> {
        return players
    }

    fun addPlayer(player: GamePlayer) {
        players.add(player)
    }

    fun wipePlayers() {
        players = ArrayList()
        kits = HashMap()
        teams = ArrayList()
    }

    fun warpPlayers(): Boolean {
        println("warp players called")
        for (player in players) {
            Main.preventInventoryCloseList.remove(player.bukkitPlayer)
            player.bukkitPlayer.closeInventory()
            Main.pregame.add(player.bukkitPlayer)
        }
        for (player in players) {
            var inTeam = false
            for (team in teams) {
                if (team.players.contains(player)) inTeam = true
            }
            if (!inTeam) {
                var addTo: Team? = null
                for (team in teams) {
                    if (team.players.size < Main.skyWarsConfig.getInt("MaximumPlayers") / Main.skyWarsConfig.getInt("TeamCount")) {
                        addTo = team
                        break
                    }
                }
                addTo?.addPlayer(player)
            }
        }

        // OLD TEAM CODE

//        List<GamePlayer> random = new ArrayList<>();
//        List<GamePlayer> chosen = new ArrayList<>();
//        List<GamePlayer> editList = new ArrayList<>(players);
//
//        for (GamePlayer player : players) {
//            if (!editList.contains(player)) continue;
//
//            int slot = teammatePrefs.get(player.getBukkitPlayer());
//            if (teamPickerGui.getBukkitInventory().getItem(slot).getType().equals(Material.RED_STAINED_GLASS_PANE)) random.add(player);
//            if (teamPickerGui.getBukkitInventory().getItem(slot).getType().equals(Material.BARRIER)) random.add(player);
//            else {
//                chosen.add(player);
//            }
//        }
//
//        for (GamePlayer player : chosen) {
//            int slot = teammatePrefs.get(player.getBukkitPlayer());
//
//            try {
//                Player preferred = Bukkit.getPlayer(Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(slot)).getName());
//                GamePlayer gp = null;
//                for (GamePlayer g : players) if (g.getBukkitPlayer().equals(preferred)) gp = g;
//
//                if (random.contains(gp) || Utils.getPlayerFromSkull(teamPickerGui.getBukkitInventory().getItem(teammatePrefs.get(preferred))).equals(player.getBukkitPlayer())) {
//                    editList.remove(player);
//                    editList.remove(gp);
//                    random.remove(gp);
//
//                    Team team = new Team();
//                    team.addPlayer(player);
//                    team.addPlayer(gp);
//                    teams.add(team);
//                } else random.add(player);
//            } catch (Exception e) { random.add(player); }
//        }
//
//        List<GamePlayer> available = new ArrayList<>(random);
//        for (GamePlayer player : random) {
//            if (!available.contains(player)) continue;
//
//            available.remove(player);
//            GamePlayer teammate = null;
//            if (!(available.size() == 0)) teammate = available.get(0);
//
//            available.remove(teammate);
//            Team team = new Team();
//            team.addPlayer(player);
//            team.addPlayer(teammate);
//
//            teams.add(team);
//        }
//
//        System.out.print("teams found");
        val cageBlocks: MutableList<Block> = ArrayList()


        // Warp players to starting points.
        println(map!!.spawns.size)
        println(players.size)
        return if (map!!.spawns.size <= players.size / 2) false else {
            val editableSpawnList = map!!.spawns
            for (team in teams) {
                val r = Random()
                val spawn = editableSpawnList[r.nextInt(editableSpawnList.size)]
                editableSpawnList.remove(spawn)
                println(map!!.bukkitWorld.name)
                println(spawn.world!!.name)
                spawn.world = Bukkit.getWorld(map!!.bukkitWorld.name)

                // construct cage
                val cageBaseBlock = spawn.clone()
                cageBaseBlock.y = cageBaseBlock.y + 4
                val ironBlocks: MutableList<Block> = ArrayList()
                val ironBars: MutableList<Block> = ArrayList()
                ironBlocks.add(cageBaseBlock.block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), cageBaseBlock.blockY.toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), cageBaseBlock.blockY.toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), cageBaseBlock.blockY.toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                for (height in 1..5) {
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ + 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 2).toDouble()).block)
                    ironBars.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 2).toDouble(), (cageBaseBlock.blockY + height).toDouble(), (cageBaseBlock.blockZ - 2).toDouble()).block)
                }
                ironBlocks.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), (cageBaseBlock.blockY + 5).toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), cageBaseBlock.blockZ.toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, cageBaseBlock.blockX.toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX + 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ + 1).toDouble()).block)
                ironBlocks.add(Location(cageBaseBlock.world, (cageBaseBlock.blockX - 1).toDouble(), (cageBaseBlock.blockY + 5).toDouble(), (cageBaseBlock.blockZ - 1).toDouble()).block)
                cageBlocks.addAll(ironBars)
                cageBlocks.addAll(ironBlocks)
                for (b in ironBlocks) b.type = Material.IRON_BLOCK
                for (b in ironBars) b.type = Material.IRON_BARS
                println("cages made")
                spawn.y = spawn.y + 6
                for (player in team.players) {
                    player.bukkitPlayer.teleport(spawn)
                    player.bukkitPlayer.inventory.setItem(0, Utils.getItemStackWithNameAndLore(Material.CHEST, "§6Select Kit", ArrayList()))
                }
                println("players teleported")
            }
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { for (player in players) player.bukkitPlayer.sendTitle("5", "", 0, 20, 0) }, 200)
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { for (player in players) player.bukkitPlayer.sendTitle("4", "", 0, 20, 0) }, 220)
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { for (player in players) player.bukkitPlayer.sendTitle("3", "", 0, 20, 0) }, 240)
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { for (player in players) player.bukkitPlayer.sendTitle("2", "", 0, 20, 0) }, 260)
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { for (player in players) player.bukkitPlayer.sendTitle("1", "", 0, 20, 0) }, 280)
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
                for (b in cageBlocks) b.type = Material.AIR
                for (player in players) {
                    player.bukkitPlayer.inventory.setItem(0, ItemStack(Material.AIR))
                    player.bukkitPlayer.closeInventory()
                    Main.pregame.remove(player.bukkitPlayer)
                    if (kits.containsKey(player.bukkitPlayer)) {
                        for (item in kits[player.bukkitPlayer]!!.items) player.bukkitPlayer.inventory.addItem(item.clone())
                    }
                }
            }, 300)

            // Modifiers
            for (player in players) {
                player.bukkitPlayer.maxHealth = settings.maxHealth.toDouble()
                player.bukkitPlayer.walkSpeed = (0.2 * settings.speedMultiplier).toFloat()
                if (settings.jumpMultiplier > 1) {
                    player.bukkitPlayer.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 9999, settings.jumpMultiplier - 1))
                }
            }
            val r = Random()
            // Schedule random events
            if (settings.isZombieHordeEvent) {
                val task: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        for (player in players) player.bukkitPlayer.sendMessage("Zombie horde event!")
                        val r = Random()
                        for (i in 0..19) {
                            val spawn = map!!.midChests[r.nextInt(map!!.midChests.size)]
                            spawn.y = spawn.y + 2
                            map!!.bukkitWorld.spawnEntity(spawn, EntityType.ZOMBIE)
                        }
                    }
                }
                task.runTaskLater(Main.instance, r.nextInt(6700) + 300.toLong())
                tasks.add(task)
            }
            if (settings.isAnvilRainEvent) {
                val task: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        for (player in players) player.bukkitPlayer.sendMessage("Anvil rain event!")
                        val r = Random()
                        for (i in 0..19) {
                            object : BukkitRunnable() {
                                override fun run() {
                                    val spawn = players[r.nextInt(players.size)].bukkitPlayer.location
                                    spawn.y = spawn.y + 16
                                    spawn.x = spawn.x + (r.nextInt(6) - 3)
                                    spawn.z = spawn.z + (r.nextInt(6) - 3)
                                    map!!.bukkitWorld.spawnFallingBlock(spawn, Material.ANVIL, 0.toByte())
                                }
                            }.runTaskLater(Main.instance, i * 5.toLong())
                        }
                    }
                }
                task.runTaskLater(Main.instance, r.nextInt(6700) + 300.toLong())
                tasks.add(task)
            }
            if (settings.isHorseMountEvent) {
                val task: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        for (player in players) {
                            player.bukkitPlayer.sendMessage("Horse mount event!")
                            val horse = map!!.bukkitWorld.spawnEntity(player.bukkitPlayer.location, EntityType.HORSE) as Horse
                            horse.inventory.saddle = ItemStack(Material.SADDLE)
                            horse.isTamed = true
                            horse.addPassenger(player.bukkitPlayer)
                        }
                    }
                }
                task.runTaskLater(Main.instance, r.nextInt(6700) + 300.toLong())
                tasks.add(task)
            }
            if (settings.isBlockDecayEvent) {
                val task: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        for (player in players) {
                            player.bukkitPlayer.sendMessage("Block decay event!")

                            // TODO: This
                        }
                    }
                }
                task.runTaskLater(Main.instance, r.nextInt(6700) + 300.toLong())
                tasks.add(task)
            }
            true
        }
    }

    fun beginGame() {
        // Populate the Teams list with teams.
        val wools: MutableList<Material> = ArrayList()
        wools.add(Material.WHITE_WOOL)
        wools.add(Material.BLACK_WOOL)
        wools.add(Material.BLUE_WOOL)
        wools.add(Material.BROWN_WOOL)
        wools.add(Material.CYAN_WOOL)
        wools.add(Material.GRAY_WOOL)
        wools.add(Material.GREEN_WOOL)
        wools.add(Material.LIME_WOOL)
        wools.add(Material.MAGENTA_WOOL)
        wools.add(Material.ORANGE_WOOL)
        wools.add(Material.PINK_WOOL)
        wools.add(Material.PURPLE_WOOL)
        wools.add(Material.RED_WOOL)
        wools.add(Material.YELLOW_WOOL)
        for (i in 0 until Main.skyWarsConfig.getInt("TeamCount")) {
            val team = Team()
            val icon = wools[i]
            team.icon = icon
            teams.add(team)
        }

        // Begin the game. Chests have already been filled.
        // Show every player an inventory to choose teams, then warp teams once everyone has picked a teammate.
        teamPickerGui = TeamPickerGui(players[0].bukkitPlayer, this)
        for (player in players) {
            Main.preventInventoryCloseList.remove(player.bukkitPlayer)
            player.bukkitPlayer.openInventory(teamPickerGui!!.bukkitInventory)
            Main.preventInventoryCloseList.remove(player.bukkitPlayer)
        }

        // Give players 15 seconds to pick teammates, then warp them
        Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
            for (player in players) {
                Main.preventInventoryCloseList.remove(player.bukkitPlayer)
                player.bukkitPlayer.closeInventory()
            }
            warpPlayers()
        }, 300)
    }

    fun setPlayers(players: MutableList<GamePlayer>) {
        this.players = players
    }

    fun getTeams(): List<Team> {
        return teams
    }

    fun setTeams(teams: MutableList<Team>) {
        this.teams = teams
    }

    fun wipePlayersWithDelay(delay: Int) {
        val game = this
        object : BukkitRunnable() {
            override fun run() {
                tasks = ArrayList()
                players = ArrayList()
                kits = HashMap()
                teams = ArrayList()
                restoreBackup()
                settings = SkyWarsSettings()
                Main.runningGames.remove(game)
                Main.waitingGames.add(game)
                Main.queue.processQueue(game)
            }
        }.runTaskLater(Main.instance, delay.toLong())
    }

    fun cancelTasks() {
        for (task in tasks) {
            task.cancel()
        }
    }

    fun removePlayer(player: GamePlayer?) {
        players.remove(player)
    }

    fun setKit(player: Player, kit: Kit?) {
        kits[player] = kit
    }

    init {
        players = ArrayList()
        settings = SkyWarsSettings()
        teams = ArrayList()
    }
}