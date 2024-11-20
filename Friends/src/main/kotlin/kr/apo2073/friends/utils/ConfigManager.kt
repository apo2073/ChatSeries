package kr.apo2073.friends.utils

import kr.apo2073.friends.mcFriend
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

class ConfigManager(private var player:Player) {
    private val plugin = mcFriend.instance
    private var file: File
    private lateinit var config: YamlConfiguration
    init {
        file = getFilePath()
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
    }
    
    private fun getFilePath(): File {
        return File(plugin.dataFolder, "friend/${player.uniqueId}.yml")
    }

    fun setValue(path: String, value: Any) {
        try {
            config.set(path, value)
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun getValue(path: String): Any = config.get(path) ?: "NULL"
    fun getStringList(path: String): MutableList<String> = config.getStringList(path)
    fun remove() { if (file.exists()) file.delete() }
}