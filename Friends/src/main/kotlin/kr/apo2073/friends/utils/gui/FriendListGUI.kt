package kr.apo2073.friends.utils.gui

import kr.apo2073.friends.enums.FLType
import kr.apo2073.friends.enums.FriendStat
import kr.apo2073.friends.utils.friends
import kr.apo2073.lib.Items.ItemBuilder
import kr.apo2073.lib.Plugins.txt
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class FriendListGUI: InventoryHolder, Listener {
    private var inv=Bukkit.createInventory(this, 9*6, txt("친구 목록"))
    constructor()
    constructor(type: FLType) {
        inv=Bukkit.createInventory(this, 9*6, txt(when(type) {
            FLType.DELETE-> "친구 삭제"
            FLType.DEFAULT-> "친구 목록"
        }))
    }
    fun get(player: Player):Inventory {
        val list=player.friends().getFriendList(FriendStat.TOTAL)
        return inv.apply { 
            list.forEach { name-> val frds=Bukkit.getPlayer(name) ?: return@forEach
                addItem(ItemBuilder(Material.PLAYER_HEAD).setOwner(name).setDisplayName("${
                    if (frds.isOnline) "§a"
                    else "§c"}${name}").build()) }
        }
    }
    
    override fun getInventory(): Inventory = inv
}