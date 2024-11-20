package kr.apo2073.chatGroup.utils

import kr.apo2073.chatGroup.ChatGroup
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Group(private var name: String) {
    private val plugin = ChatGroup.instance
    private var players= mutableSetOf<Player>()
    private val config = ConfigManager(name)

    init {
        plugin.config.set("groups", plugin.config.getStringList("groups").add(name))
        plugin.saveDefaultConfig()
        config.create()
        (config.getValue("players") as List<*>).forEach {
            s-> players.add(Bukkit.getPlayer(s.toString()) ?: return@forEach)
        }
    }

    fun addPlayer(player: Player) {
        players.add(player)
        config.setValue("players", players)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
        config.setValue("players", players)
    }
    
    fun isContainPlayer(player: Player):Boolean =players.contains(player)

    fun getPlayers(): Set<Player> = players
    fun getPlayerCount(): Int = players.size
    fun remove() {
        plugin.config.set("groups", plugin.config.getStringList("groups").remove(name))
        plugin.saveDefaultConfig()
        config.remove()
    }

    fun getName(): String {
        return name
    }
    fun setName(newName:String) {
        config.rename(newName)
    }
    fun getConfig() = ConfigManager(name)
}