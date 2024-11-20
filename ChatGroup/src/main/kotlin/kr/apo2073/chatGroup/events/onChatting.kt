package kr.apo2073.chatGroup.events

import io.papermc.paper.event.player.AsyncChatEvent
import kr.apo2073.chatGroup.utils.getGroup
import kr.apo2073.chatGroup.utils.groupManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class onChatting:Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChatting(e: AsyncChatEvent) {
        val player=e.player
        val players=player.groupManager().getPlayers()
        if (player.getGroup() == "default") return
        e.viewers().clear()
        e.viewers().addAll(players)
    }
}