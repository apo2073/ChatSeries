package kr.apo2073.speechBubble.events

import io.papermc.paper.event.player.AsyncChatEvent
import kr.apo2073.speechBubble.ChatBubble
import net.kyori.adventure.text.Component
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable

class onChat:Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onChat(e: AsyncChatEvent) {
        ChatBubble.instance.reloadConfig()
        if (ChatBubble.instance.config.getBoolean("only")) e.isCancelled = true
        val chat = Component.text(ChatBubble.instance
            .config.getString("bubbleForm", "{chat}") ?: "{chat}")
            .replaceText { builder -> builder.match("\\{chat}").replacement(e.originalMessage()) }
        val armor = e.player.world.spawn(e.player
            .location.add(0.0, 2.0, 0.0), 
            ArmorStand::class.java) { a -> 
            a.isInvisible=true
            a.isCustomNameVisible=true 
            a.isSmall=true
            a.isMarker=false
            a.customName(chat)
        }

        object : BukkitRunnable() {
            var ticks = 0
            override fun run() {
                if (ticks >= 14) {
                    armor.remove()
                    cancel()
                } else {
                    armor.teleport(armor.location.add(0.0, 0.0357, 0.0))
                    ticks++
                }
            }
        }.runTaskTimer(ChatBubble.instance, 1L, 1L)
    }
}