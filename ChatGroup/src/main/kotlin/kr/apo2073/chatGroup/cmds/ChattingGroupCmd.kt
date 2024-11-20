package kr.apo2073.chatGroup.cmds

import kr.apo2073.chatGroup.ChatGroup
import kr.apo2073.chatGroup.utils.*
import kr.apo2073.lib.Plugins.txt
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class ChattingGroupCmd: TabExecutor {
    private val plugin=ChatGroup.instance
    val perfix=MiniMessage.miniMessage().deserialize("<gradient:#CAC0F8:#A28FFC>[ CHAT ] </gradient>")

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p0 !is Player) return false
        val player=p0 as Player
        if (!player.hasPermission("group.cmd")) return true
        when(p3.size) {
            0-> { player.sendMessage(perfix.append(txt("§l현재 접속한 챗그룹 §r: §a${player.getGroup()}"))) }
            1-> { 
                if (p3[0] == "목록") plugin.config.getStringList("groups").forEach { 
                    player.sendMessage(perfix.append(txt("[-] $it")))
                } else if (p3[0] == "나가기") {
                    player.groupManager().getPlayers().forEach {
                        it.sendMessage(perfix.append(txt("플레이어 §l§a${player.name}§r이(가) 그룹에서 나갔습니다")))
                    }
                    val gr=player.getGroup()
                    player.sendMessage(perfix.append(txt("챗그룹 §l§a${player.getGroup()}에서 나갔습니다")))
                    player.leaveGroup()
                    if (Group(gr).getPlayers().isEmpty()) Group(gr).remove()
                }
                else player.sendMessage(perfix.append(txt("올바른 명령어를 입력하세요").color(NamedTextColor.RED))) 
            }
            2-> {
                if (p3[0] == "참여") {
                    val name=p3[1]
                    player.groupManager().getPlayers().forEach {
                        it.sendMessage(perfix.append(txt("플레이어 §l§a${player.name}§r이(가) 그룹에 참여했습니다")))
                    }
                    player.setGroup(name)
                    player.sendMessage(perfix.append(txt("챗그룹 ${name}에 참여했습니다")
                        .append(txt("[ §l§a${player.groupManager().getMemberCount()}§r명 온라인 ]"))))
                }  else if (p3[0] == "초대"){
                    val fr=Bukkit.getPlayer(p3[1]) ?: run { 
                        player.sendMessage(perfix.append(txt("해당 플레이어가 존재하지 않습니다")))
                        return true
                    }
                    fr.sendMessage(
                        perfix
                            .append(txt("플레이어 §l§a${player.name}§r이(가) 그룹 초대장을 보냈습니다"))
                            .append(txt("[ 참여하기 ]").color(NamedTextColor.GREEN).clickEvent(
                                ClickEvent.runCommand("/그룹 참여 ${player.getGroup()}")
                            ))
                    )
                    player.sendMessage(perfix.append(txt("플레이어 §l§a${fr.name}§r에게 그룹 초대장을 보냈습니다")))
                }
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
        when(p3.size) {
            1-> {
                tab.apply { 
                    add("참여")
                    add("나가기")
                    add("초대")
                    add("목록")
                }
            }
            2-> {
                if (p3[0] == "초대") {
                    Bukkit.getOnlinePlayers().forEach { tab.add(it.name) }
                } else {
                    tab.addAll(plugin.config.getStringList("groups"))
                }
            }
        }
        return tab
    }
}