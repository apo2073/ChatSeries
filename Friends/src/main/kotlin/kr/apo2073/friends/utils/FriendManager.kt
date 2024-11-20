package kr.apo2073.friends.utils

import kr.apo2073.friends.enums.FriendStat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class FriendManager(private var player: Player) {
    private val config=ConfigManager(this.player)
    
    fun getFriendList(stat: FriendStat): MutableList<String> {
        val fl=config.getStringList("friends") 
        return when(stat) {
            FriendStat.TOTAL-> fl
            FriendStat.ONLINE-> fl.filter { Bukkit.getPlayer(it)?.isOnline ?: false }.toMutableList()
            FriendStat.OFFLINE-> fl.filter { !(Bukkit.getPlayer(it)?.isOnline ?: true) }.toMutableList()
        }
    }
    fun addFriend(friend:Player) { config.setValue("friends", getFriendList(FriendStat.TOTAL).add(friend.name)) }
    fun removeFriend(friend: Player) { config.setValue("friends", getFriendList(FriendStat.TOTAL).remove(friend.name)) }
    fun resetFriend() = ConfigManager(player).remove()
    fun hasFriends():Boolean { return getFriendList(FriendStat.TOTAL).isNotEmpty() }
    fun isFriends(person: Player): Boolean { return getFriendList(FriendStat.TOTAL).contains(person.name) }
    fun getConfig():ConfigManager=config
}
fun Player.friends():FriendManager=FriendManager(this)