package kr.apo2073.chatGroup

import org.bukkit.plugin.java.JavaPlugin

class ChatGroup : JavaPlugin() {
    companion object{lateinit var instance:ChatGroup}
    override fun onEnable() {
        instance=this
        
    }
    
    fun getGroups():List<String> =config.getStringList("groups").toMutableList()

    override fun onDisable() {
        
    }
}
