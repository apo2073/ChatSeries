package kr.apo2073.chatGroup

import kr.apo2073.chatGroup.cmds.ChattingGroupCmd
import kr.apo2073.chatGroup.events.onChatting
import kr.apo2073.lib.Plugins.Register
import org.bukkit.plugin.java.JavaPlugin

class ChatGroup : JavaPlugin() {
    companion object{lateinit var instance:ChatGroup}
    override fun onEnable() {
        instance=this
        saveDefaultConfig()
        Register(this)
            .resistEventListener(onChatting())
            .resistTabExecutor("그룹", ChattingGroupCmd())
    }
    
    fun getGroups():List<String> =config.getStringList("groups").toMutableList()

    override fun onDisable() {
        
    }
}
