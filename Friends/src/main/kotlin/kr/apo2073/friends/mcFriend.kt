package kr.apo2073.friends

import org.bukkit.plugin.java.JavaPlugin

class mcFriend : JavaPlugin() {
    companion object {lateinit var instance: mcFriend}
    override fun onEnable() {
        instance=this
    }
}
