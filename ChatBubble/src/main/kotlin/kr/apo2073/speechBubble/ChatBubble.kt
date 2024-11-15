package kr.apo2073.speechBubble

import kr.apo2073.lib.Plugins.Register
import kr.apo2073.speechBubble.events.onChat
import org.bukkit.plugin.java.JavaPlugin

class ChatBubble : JavaPlugin() {
    companion object {lateinit var instance:ChatBubble}
    override fun onEnable() {
        instance=this
        saveDefaultConfig()
        Register(this).resistEventListener(onChat())
    }
}
