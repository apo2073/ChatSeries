package kr.apo2073.chatGroup.utils

import kr.apo2073.chatGroup.ChatGroup
import org.bukkit.entity.Player

fun Player.getGroup():String  {
    ChatGroup.instance.getGroups().forEach { g ->
        if (Group(g).isContainPlayer(this)) return g
    }
    return "default"
}
fun Player.setGroup(group: String) {
    if (GroupManager(group).isGroupExist()) {
        GroupManager(group).joinPlayer(this)
    } else {
        GroupManager(group).create()
    }
}
fun Player.leaveGroup() {
    setGroup("default")
}

fun Player.groupManager(): GroupManager {
    return GroupManager(getGroup())
}