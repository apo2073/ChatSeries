package kr.apo2073.chatFroms.events

import io.papermc.paper.event.player.AsyncChatEvent
import kr.apo2073.chatFroms.ChatFroms
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class onChat: Listener {
    val chatForm= ChatFroms.instance
    @EventHandler(priority = EventPriority.LOW)
    fun onChatting(e: AsyncChatEvent) {
        chatForm.reloadConfig()
        e.isCancelled=true
        var format=chatForm.config
            .getString("form", "<{name}> {chat}") ?: return
        val regex = Regex("\\{(.*?)}")
        regex.findAll(format).forEach { match->
            val placeholder = match.groupValues[1]
            val replacement = chatForm.config.getString("papi.$placeholder")?.let {
                PlaceholderAPI.setPlaceholders(e.player, it)
            } ?: ""
            format = format.replace("{${placeholder}}", replacement)
        }
        e.message(Component.text(format).replaceText {
            builder-> builder.match("\\{chat}").replacement(e.originalMessage())
        })
    }
}