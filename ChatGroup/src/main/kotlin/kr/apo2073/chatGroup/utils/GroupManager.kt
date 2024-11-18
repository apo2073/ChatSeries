package kr.apo2073.chatGroup.utils

import ConfigManager
import kr.apo2073.chatGroup.ChatGroup
import org.bukkit.entity.Player

class GroupManager(private var name: String) {
    fun create() = Group(name)
    fun delete() = Group(name).remove()
    fun getPlayers():Set<Player> = Group(name).getPlayers()
    fun joinPlayer(player: Player)=Group(name).addPlayer(player)
    fun leftPlayer(player: Player)=Group(name).removePlayer(player)
    fun getMemberCount():Int = Group(name).getPlayerCount()
    fun getConfig() = Group(name).getConfig()
    fun getGroup():Group = Group(name)
    fun isGroupExist():Boolean=ConfigManager(name).isExist()
}