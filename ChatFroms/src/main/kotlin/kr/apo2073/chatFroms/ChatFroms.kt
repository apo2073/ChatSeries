package kr.apo2073.chatFroms

import kr.apo2073.chatFroms.events.onChat
import kr.apo2073.lib.Plugins.Register
import org.bukkit.plugin.java.JavaPlugin

class ChatFroms : JavaPlugin() {
    companion object {lateinit var instance:ChatFroms}
    override fun onEnable() {
        instance=this
        saveDefaultConfig()
        Register(this).resistEventListener(onChat())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
