package kr.apo2073.friends.utils

import kr.apo2073.friends.mcFriend
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.io.File
import java.io.IOException


class GiftConfigManager(private var player:Player) {
    private val plugin = mcFriend.instance
    private var file: File
    private lateinit var config: YamlConfiguration
    init {
        file = getFilePath()
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
    }
    
    private fun getFilePath(): File {
        return File(plugin.dataFolder, "gift/${player.uniqueId}.yml")
    }

    fun setValue(path: String, value: Any) {
        try {
            config.set(path, value)
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun saveInventory(player: Player, inventory: Inventory) {
        val itemsMap = inventory.contents
            .mapIndexedNotNull { index, item ->
                if (item != null) index to item else null
            }.toMap()
        config.set("items.${player.uniqueId}", itemsMap)
        try {
            config.save(file)
        } catch (e: Exception) {
            plugin.logger.severe("Failed to save inventory for player ${player.name}: ${e.message}")
        }
    }
    fun getConfiguration(): YamlConfiguration = config
    
    fun getValue(path: String): Any = config.get(path) ?: "NULL"
    fun getStringList(path: String): MutableList<String> = config.getStringList(path)
}