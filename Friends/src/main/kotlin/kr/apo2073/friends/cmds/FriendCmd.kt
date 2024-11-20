package kr.apo2073.friends.cmds

import kr.apo2073.lib.Plugins.txt
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class FriendCmd: TabExecutor {
    val perfix= MiniMessage.miniMessage().deserialize("<gradient:#CAC0F8:#A28FFC>[ Friend ] </gradient>")
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p0 !is Player) return false
        val player=p0 as Player
        if (!player.hasPermission("fri.cmd")) return true
        when(p3.size) {
            0-> {player.sendMessage(perfix.append(txt("/친구 [추가/삭제/목록]")))}
        }
        return true
    }
    
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>,
    ): MutableList<String> {
        val tab = mutableListOf<String>()
        tab.apply {
            when(p3.size) {
                1-> {
                    add("추가")
                    add("삭제")
                    add("목록")
                }
                2-> { Bukkit.getOnlinePlayers().forEach { add(it.name) } } 
            }
        }
        return tab
    }
}