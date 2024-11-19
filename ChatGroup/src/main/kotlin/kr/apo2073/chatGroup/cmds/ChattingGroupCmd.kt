package kr.apo2073.chatGroup.cmds

import kr.apo2073.chatGroup.utils.getGroup
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ChattingGroupCmd(var plugin: JavaPlugin): TabExecutor {
    init {
        plugin.getCommand("그룹")?.setExecutor(this)
        plugin.getCommand("그룹")?.tabCompleter=this
    }

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p0 !is Player) return false
        val player=p0 as Player
        when(p3.size) {
            0-> {
                player.sendMessage("현제 그룹 :: ${player.getGroup()}")
            }
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String> {
        val tab= mutableListOf<String>()
        return tab
    }
}