package me.therealmck.skywars.data

import me.therealmck.skywars.data.loot.LootTable

class SkyWarsSettings {
    var maxHealth: Int
    var jumpMultiplier: Int
    var speedMultiplier: Int
    var islandLootTable: LootTable
    var midLootTable: LootTable
    var isAnvilRainEvent = false
    var isBlockDecayEvent = false
    var isHorseMountEvent = false
    var isZombieHordeEvent = false

    constructor(maxHealth: Int, jumpMultiplier: Int, speedMultiplier: Int, table: LootTable, midTable: LootTable) {
        this.maxHealth = maxHealth
        this.jumpMultiplier = jumpMultiplier
        this.speedMultiplier = speedMultiplier
        islandLootTable = table
        midLootTable = midTable
    }

    constructor() {
        maxHealth = 20
        jumpMultiplier = 1
        speedMultiplier = 1
        islandLootTable = LootTable(true)
        midLootTable = LootTable(false)
    }

}