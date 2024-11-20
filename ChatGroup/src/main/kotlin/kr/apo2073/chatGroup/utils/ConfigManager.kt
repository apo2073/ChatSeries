package kr.apo2073.chatGroup.utils

import kr.apo2073.chatGroup.ChatGroup
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class ConfigManager(private var name: String) {
    private val plugin = ChatGroup.instance
    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private fun getFilePath(name: String): File {
        return File(plugin.dataFolder, "group/$name.yml")
    }

    fun create() {
        file = getFilePath(name)
        if (!file.parentFile.exists()) file.parentFile.mkdirs()
        if (!file.exists()) {
            try {
                file.createNewFile()
                config = YamlConfiguration.loadConfiguration(file)
                config.set("name", name)
                config.save(file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file)
        }
    }

    fun isExist(): Boolean = getFilePath(name).exists()

    fun setValue(path: String, value: Any) {
        try {
            config.set(path, value)
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getValue(path: String): Any = config.get(path) ?: "NULL"

    fun rename(newName: String) {
        val newFile = getFilePath(newName)
        if (!file.exists() && newFile.exists()) return
        if (!file.renameTo(newFile)) return
        name = newName
        file = newFile
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun remove() {
        if (!file.exists()) return
        file.delete()
    }
}
