package me.therealmck.skywars.data

import me.therealmck.skywars.Main
import me.therealmck.skywars.utils.Utils
import org.apache.commons.lang.WordUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Kit {
    var name: String
        private set
    var permission: String?
        private set
    private var icon: ItemStack?
    var items: List<ItemStack?>?
        private set

    constructor(identifier: String) {
        name = identifier
        val section = Main.kitsConfig.getConfigurationSection(name)!!
        permission = section.getString("permission")
        icon = section.getItemStack("icon")
        items = section.getList("items") as List<ItemStack?>?
    }

    constructor(clone: Kit) {
        name = clone.name
        permission = clone.permission
        items = clone.items
        icon = clone.icon
    }

    fun getIcon(player: Player): ItemStack {
        val lore: MutableList<String> = ArrayList()
        for (item in items!!) {
            val name = WordUtils.capitalize(item!!.type.toString().replace("_", " "))
            lore.add(String.format("%s x %s", name, item.amount))
        }
        val icon = Utils.getItemStackWithNameAndLore(icon!!.type, name, lore)
        if (!player.hasPermission(permission!!)) {
            icon.type = Material.BARRIER
            val meta = icon.itemMeta
            meta!!.lore = ArrayList()
            meta.setDisplayName("Kit locked!")
            icon.itemMeta = meta
        }
        return icon
    }

}