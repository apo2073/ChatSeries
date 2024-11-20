package kr.apo2073.friends.utils.gui

import kr.apo2073.friends.enums.FriendStat
import kr.apo2073.friends.utils.friends
import kr.apo2073.lib.Items.ItemBuilder
import kr.apo2073.lib.Plugins.txt
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.net.http.WebSocket.Listener

class FriendMainGUI:InventoryHolder, Listener {
    private var inv=Bukkit.createInventory(this, 9*4, txt("친구"))
    fun get(player: Player): Inventory {
        return inv.apply { 
            setItem(13, ItemBuilder(Material.PLAYER_HEAD)
                .setItemName(txt("친구 목록").color(TextColor.color(0xB1C29E)))
                .setOwner(player.name)
                .setDescription(arrayListOf(txt("친구 §e${
                    player.friends().getFriendList(FriendStat.TOTAL)
                } 중 §a${player.friends().getFriendList(FriendStat.ONLINE)}명 온라인")))
                .build())
        }
    }
    
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.inventory.getHolder(false) !is FriendMainGUI) return
        val ci=e.clickedInventory ?: return
        val player=e.view as Player
        val slot=e.slot
        if (slot==13) {
            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN)
        }
    }
    
    override fun getInventory(): Inventory = inv
}